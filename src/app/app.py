from functionRequests import *
from db_connection import *
from typing import Union
import uvicorn
from fastapi import FastAPI, Request
from pydantic import BaseModel
from monitoring import instrumentator
from fastapi.middleware.cors import CORSMiddleware
import hashlib
from typing import List
import logging
from logstash_async.handler import AsynchronousLogstashHandler

app = FastAPI()
app.type = "00"
tun_, conn_ = connect_db()
app.state.connection = conn_

# def add_smthng(smthng):
#     global connection
#     connection += [smthng]
#     return True

tunnel, conn = connect_db()


def get_logger():
    host = 'localhost'
    port = 5959

    logger = logging.getLogger('python-logstash-logger')
    logger.setLevel(logging.INFO)
    logger.addHandler(AsynchronousLogstashHandler(host, port))

    return logger


logger = get_logger()


#  метрики доступны по /metrics
# @app.on_event("startup")
# async def startup():
#     instrumentator.instrument(app).expose(app, endpoint="/metrics", include_in_schema=True, should_gzip=True)

class CV(BaseModel):
    id: int
    cr_user: int
    cv_name: str
    cv_text: str
    cr_date: datetime.datetime
    img_id: int
    cv_category: int
    salary: int
    experience_content: str
    education_content: str


class Vacancy(BaseModel):
    id: int
    cr_user: int
    vac_name: str
    vac_text: str
    cr_date: datetime.datetime
    vac_category: int
    img_id: int


class Profile(BaseModel):
    id: int
    name: str
    surname: str
    age: str
    gender_id: int
    img_id: int
    cr_user: int


class User(BaseModel):
    id: int
    login: str
    password: str
    name: str
    role_id: int


@app.get("/")
def read_root(request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, request: Request, q: Union[str, None] = None):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return {"item_id": item_id, "q": q}


@app.get("/next_worker/")
def next_worker(request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_next_user(app.state.connection).to_json()


@app.get("/next_vacancy/")
def next_vacancy(request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_next_vacancy(app.state.connection).to_json()


@app.get("/cv/{cv_id}")
def get_cv_by_id(cv_id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    cv = get_cv(app.state.connection, cv_id)
    return cv.to_json()


@app.get("/vacancy/{vac_id}")
def get_vacancy_by_id(vac_id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    vac = get_vacancy(app.state.connection, vac_id)
    return vac.to_json()


@app.get("/history/vacancy/{vacancy_id}")
def liked_workers(vacancy_id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    workers_ids = get_liked_workers_ids(app.state.connection, vacancy_id)
    liked_workers_for_vacancy = []
    for worker in workers_ids:
        liked_workers_for_vacancy.append(worker[0])
    return {"workers": liked_workers_for_vacancy}


@app.get("/history/worker/{worker_id}")
def liked_vacancies(worker_id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    vacancies_ids = get_liked_vacancies_ids(app.state.connection, worker_id)
    liked_vacancies_by_worker = []
    for worker in vacancies_ids:
        liked_vacancies_by_worker.append(worker[0])
    return {"workers": liked_vacancies_by_worker}


class Like(BaseModel):
    job_id: int
    worker_id: int


@app.post("/worker_like/")
async def like_from_worker(like: Like, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    vacancies_ids = get_liked_vacancies_ids(app.state.connection, like.worker_id)
    post_like_from_worker(app.state.connection, like.job_id, like.worker_id)

    if like.job_id in vacancies_ids:
        return {"match": True, "job_id": like.job_id}
    return {"match": False, "job_id": like.job_id}


@app.post("/vacancy_like/")
async def like_from_vacancy(like: Like, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    workers_ids = get_liked_workers_ids(app.state.connection, like.job_id)
    post_like_from_vacancy(app.state.connection, like.job_id, like.worker_id)

    if like.worker_id in workers_ids:
        return {"match": True, "worker_id": like.worker_id}
    return {"match": False, "worker_id": like.worker_id}


@app.get("/vacancy/vacancy_categories")
def vacancy_categories(request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_vacancy_categories(app.state.connection)


@app.post("/login/token/{login}/{tok}")
def login_with_token(login: str, tok: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    res = check_token(app.state.connection, login, tok)
    return {"login": res}


@app.post("/vacancy/new")
def create_new_vacancy(vacancy: Vacancy, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    creat_vacancy(app.state.connection, vacancy)


@app.post("/cv/new")
def create_new_vacancy(cv: CV, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    create_cv(app.state.connection, cv)


@app.post("/vacancy/update")
def create_new_vacancy(vacancy: Vacancy, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    update_vacancy(app.state.connection, vacancy)


@app.post("/cv/update")
def create_new_vacancy(cv: CV, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    update_cv(app.state.connection, cv)


@app.post("/login/password/{login}/{password}")
def login_with_password(login: str, password: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    token = try_login(app.state.connection, login, password)
    return {"token": token}


@app.get("/user_role/{email}")
def user_role_by_email(email: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_role(app.state.connection, email)


@app.get("/vacancy_preview_info/{email}")
def get_vacancy_preview(email: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_all_vacancy_preview(app.state.connection, email)


@app.get("/cv_preview_info/{email}")
def get_cv_preview(email: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_all_cv_preview(app.state.connection, email)


@app.get("/get_language_name_by_id/{id}")
def get_language_name_by_id(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_language_by_id(app.state.connection, id)


@app.get("/get_grade_name_by_id/{id}")
def get_grade_name_by_id(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_grade_by_id(app.state.connection, id)


@app.get("/get_gender_name_by_id/{id}")
def get_gender_name_by_id(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_gender_by_id(app.state.connection, id)


@app.get("/get_location_name_by_id/{id}")
def get_location_name_by_id(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_location_by_id(app.state.connection, id)


@app.get("/get_job_category_name_by_id/{id}")
def get_job_category_name_by_id(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_job_category_by_id(app.state.connection, id)


@app.post("/add_vacancy/")
async def add_vacancy(vacancy: Vacancy, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await add_vacancy_to_db(
        app.state.connection,
        vac_name=vacancy.vac_name,
        vac_text=vacancy.vac_text,
        cr_date=vacancy.cr_date,
        vac_category=vacancy.vac_category,
    )
    return {"status": "ok"}


@app.post("/edit_vacancy/")
async def edit_vacancy(vacancy: Vacancy, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await edit_vacancy_in_db(
        app.state.connection,
        vac_name=vacancy.vac_name,
        vac_text=vacancy.vac_text,
        cr_date=vacancy.cr_date,
        vac_category=vacancy.vac_category,
        id=vacancy.id,
    )
    return {"status": "ok"}


@app.post("/add_cv/")
async def add_cv(cv: CV, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await add_cv_to_db(
        app.state.connection,
        cv.cv_name,
        cv.cv_text,
        cv.experience_content,
        cv.education_content,
        cv.salary,
        cv.cr_date,
        cv.cv_category,
    )
    return {"status": "ok"}


@app.post("/edit_cv/")
async def edit_cv(cv: CV, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await edit_cv_in_db(
        app.state.connection,
        cv.cv_name,
        cv.cv_text,
        cv.experience_content,
        cv.education_content,
        cv.salary,
        cv.cr_date,
        cv.cv_category,
        cv.id,
    )
    return {"status": "ok"}


@app.get("/profiles/")
async def get_profiles(request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    profiles = get_profiles_json(app.state.connection)

    res = {}

    for el in profiles:
        res[el[0]] = el[1]

    return res


@app.get("/profiles/{id}")
async def get_profiles(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    arr = get_profile_by_id(app.state.connection, id)[0]

    res = {}

    res["id"] = arr[0]
    res["email"] = arr[1]
    res["login"] = arr[2]
    res["password"] = arr[3]
    res["name"] = arr[4]
    res["surname"] = arr[5]
    res["age"] = arr[6]
    res["gender_id"] = arr[7]
    res["role_id"] = arr[8]

    imgID = arr[9]
    imgExtension = arr[10]

    res["cr_user"] = arr[11]
    res["img_id"] = arr[9]
    res["profile_id"] = arr[12]

    url = "http://217.25.88.166/web_project/files/images/0/" + str(imgID) + "." + str(imgExtension)

    res["img_url"] = url
    return res


@app.get("/profile/{name}")
async def get_profile_by_name(name: str, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    arr = get_profile_with_name(app.state.connection, name)[0]

    res = {}

    res["id"] = arr[0]
    res["email"] = arr[1]
    res["login"] = arr[2]
    res["password"] = arr[3]
    res["name"] = arr[4]
    res["surname"] = arr[5]
    res["age"] = arr[6]
    res["gender_id"] = arr[7]
    res["role_id"] = arr[8]

    imgID = arr[9]
    imgExtension = arr[10]

    res["cr_user"] = arr[11]
    res["img_id"] = arr[9]
    res["profile_id"] = arr[12]

    url = "http://217.25.88.166/web_project/files/images/0/" + str(imgID) + "." + str(imgExtension)

    res["img_url"] = url
    return res


@app.post("/add_user/")
async def add_user(user: User, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await add_user_to_bd(
        app.state.connection,
        user.login,
        user.password,
        user.name,
        user.role_id,
    )

    return {"status": "ok"}


@app.post("/add_profile/")
async def add_profile(profile: Profile, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await add_profile_to_db(
        app.state.connection,
        profile.name,
        profile.surname,
        profile.age,
        profile.gender_id,
    )
    return {"status": "ok"}


@app.post("/edit_profile/")
async def edit_profile(profile: Profile, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    edit_profile_in_db(
        app.state.connection,
        profile.name,
        profile.surname,
        profile.age,
        profile.gender_id,
        profile.id
    )

    return {"status": "ok"}


@app.get("/get_matched_users_for_vacancy/{id}")
def get_matched_users_for_vacancy(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_matches_for_vacancy(app.state.connection, id)


@app.get("/get_matched_vacancies_for_user/{id}")
def get_matched_vacancies_for_user(id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_matches_for_user(app.state.connection, id)


@app.post("/add_match/{user_id}/{vacancy_id}")
async def add_match(user_id: int, vacancy_id: int, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await insert_match(
        app.state.connection,
        user_id,
        vacancy_id
    )
    return {"status": "ok"}


class VacancyFilters(BaseModel):
    salary: int | None
    is_fulltime: bool | None
    is_distant: bool | None
    location_id: int | None
    grade_id: int | None


@app.post("/get_vacancy_ids_by_filters/")
def get_vacancy_ids_by_filters(vacancy_filters: VacancyFilters, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_vacancies_by_filters(app.state.connection, vacancy_filters.salary, vacancy_filters.is_fulltime,
                                    vacancy_filters.is_distant, vacancy_filters.location_id, vacancy_filters.grade_id)


@app.post("/set_vacancy_filters/{vac_id}")
async def set_vacancies_filters(vac_id: int, vacancy_filters: VacancyFilters, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await set_vacancy_by_filters(
        app.state.connection,
        vac_id,
        vacancy_filters.salary,
        vacancy_filters.is_fulltime,
        vacancy_filters.is_distant,
        vacancy_filters.location_id,
        vacancy_filters.grade_id
    )
    return {"status": "ok"}


class UserFilters(BaseModel):
    salary: int | None
    grade_id: int | None
    languages_ids: List[int] | None


@app.post("/get_user_ids_by_filters/")
def get_user_ids_by_filters(user_filters: UserFilters, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    return get_users_by_filters(app.state.connection, user_filters.salary, user_filters.grade_id,
                                user_filters.languages_ids)


@app.post("/set_users_filters/{user_id}")
async def set_users_filters(user_id: int, user_filters: UserFilters, request: Request):
    token = request.headers.get('token')
    if hashlib.sha256(token.encode('utf8')).hexdigest() != getCert():
        return {"status": "unauthorized"}

    await set_users_by_filters(
        app.state.connection,
        user_id,
        user_filters.salary,
        user_filters.grade_id,
        user_filters.languages_ids
    )
    return {"status": "ok"}


def getCert():
    f = open("key.crt").readlines()
    return f[0]


# General comment
# Пока что нет сущности сессии, хотя в будущем  возможно придётся переписать с расчётом на сессии,
# так что все кто редактируют код, пожалуйста держите это в уме.
# print("at least should be getting here")
def Start():
    print("oh")
    # tunnel_, conn_ = connect_db()
    # add_smthng(conn_)
    # app.state.connection = conn_
    print("ok")
    uvicorn.run("app:app", host="0.0.0.0", port=8000, log_level="info")
    print("ehm")


app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

if __name__ == '__main__':
    Start()

#    print("Something is not quite right")
#    close_ssh_tunnel(tunnel)
#    close_connection(conn)


instrumentator.instrument(app).expose(app, endpoint="/metrics", include_in_schema=True, should_gzip=True)
