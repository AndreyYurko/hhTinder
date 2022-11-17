from requests import *
from db_connection import *
from typing import Union
import uvicorn
from fastapi import FastAPI
from pydantic import BaseModel
from monitoring import instrumentator
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()  # rest api
app.type = "00"
tun_, conn_ = connect_db()
app.state.connection = conn_

# def add_smthng(smthng):
#     global connection
#     connection += [smthng]
#     return True

tunnel, conn = connect_db()  # server for DB


#  метрики доступны по /metrics
# @app.on_event("startup")
# async def startup():
#     instrumentator.instrument(app).expose(app, endpoint="/metrics", include_in_schema=True, should_gzip=True)

@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}


@app.get("/worker/{job_id}")
def next_worker(job_id: int):
    # global connection
    user = get_next_user(app.state.connection)
    return user.to_json()


@app.get("/job/{worker_id}")
def next_job(worker_id: int):
    # global connection
    print(worker_id)
    vac = get_next_vacancy(app.state.connection)
    return vac.to_json()


@app.get("/history/job/{job_id}")
def next_job(job_id: int):
    # global connection
    workers = get_liked_workers_ids(app.state.connection, job_id)
    new_workers = []
    for worker in workers:
        new_workers += [worker[0]]
    return {"workers": new_workers}


@app.get("/history/worker/{worker_id}")
def next_job(worker_id: int):
    # global connection
    vacancies = get_liked_vacancies_ids(app.state.connection, worker_id)
    new_vacancies = []
    for worker in vacancies:
        new_vacancies += [worker[0]]
    return {"workers": new_vacancies}


class Like(BaseModel):
    job_id: int
    worker_id: int


@app.post("/worker_like/")
async def like(like: Like):
    # check likes and
    vacancies_ids = get_liked_vacancies_ids(app.state.connection, like.worker_id)
    print(vacancies_ids)
    print(type(vacancies_ids))

    # create like
    post_like_from_worker(app.state.connection, like.job_id, like.worker_id)

    # if vacancies contains like.job_id then match
    if like.job_id in vacancies_ids:
        return {"match": True, "job_id": like.job_id}
    return {"match": False, "job_id": like.job_id}


@app.post("/job_like/")
async def like(like: Like):
    # check likes and
    workers_ids = get_liked_workers_ids(app.state.connection, like.job_id)
    print(workers_ids)

    # create like
    post_like_from_job(app.state.connection, like.job_id, like.worker_id)

    if like.worker_id in workers_ids:
        return {"match": True, "worker_id": like.worker_id}
    return {"match": False, "worker_id": like.worker_id}


@app.get("/vacancy/{vac_id}")
def next_vacancy(vac_id: int):
    # global connection
    vacancy = get_next_vacancy(app.state.connection)
    return vacancy.to_json()


@app.get("/cv/{cv_id}")
def next_cv(cv_id: int):
    # global connection
    cv = get_next_cv(app.state.connection)
    return cv.to_json()


@app.get("/voc/job_categories")
def job_categories():
    categories = get_job_categories(app.state.connection)
    return categories


@app.post("/login/token/{login}/{token}")
def login_with_token(login: str, token: str):
    res = check_token(app.state.connection, login, token)
    return {"login": res}


@app.post("/login/password/{login}/{password}")
def login_with_password(login: str, password: str):
    token = try_login(app.state.connection, login, password)
    return {"token": token}


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

# if __name__ == '__main__':
#     Start()
#     print("Something is not quite right")
#     close_ssh_tunnel(tunnel)
#     close_connection(conn)

instrumentator.instrument(app).expose(app, endpoint="/metrics", include_in_schema=True, should_gzip=True)
