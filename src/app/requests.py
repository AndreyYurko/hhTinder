import datetime
import random
from models import *
from sql_queries import Queries
import utils, secrets


def execute_sql_query(conn, query, type='default'):
    records = None
    try:
        print("Executing SQL Query..")
        db_cursor = conn.cursor()
        db_cursor.execute(query)
        if type == 'default':
            records = db_cursor.fetchall()
        else:
            conn.commit()
        db_cursor.close()
        print("SUCCESS: Query was successful")
        return records
    except Exception as ex:
        print("FAILED: Wrong query")
        print(ex)


# get user by id
def get_user(conn, user_id):
    message_template_select_user = Queries.GET_USER_BY_ID
    user_data = execute_sql_query(conn, message_template_select_user.format(id=user_id))
    if len(user_data) == 0:
        print("No user with id =", user_id)
        return None
    else:
        user = User(user_data[0])
        return user


# get random user
def get_next_user(conn):
    ids = execute_sql_query(conn, Queries.GET_USER_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    return get_user(conn, random_id)


# get vacancy by id
def get_vacancy(conn, vac_id):
    message_template_select_vacancy = Queries.GET_VACANCY_BY_ID
    vacancy_data = execute_sql_query(conn, message_template_select_vacancy.format(id=vac_id))
    try:
        if len(vacancy_data) == 0:
            print("No vacancy with id =", vac_id)
            return None
        else:
            vacancy = Vacancy(vacancy_data[0])
            return vacancy
    except:
        return Vacancy()


# get random vacancy
def get_next_vacancy(conn):
    ids = execute_sql_query(conn, Queries.GET_VACANCY_IDS)
    try:
        random_id = ids[random.randint(0, len(ids) - 1)][0]
    except:
        return Vacancy()
    return get_vacancy(conn, random_id)


# get cv by id
def get_cv(conn, cv_id):
    message_template_select_cv = Queries.GET_CV_BY_ID
    cv_data = execute_sql_query(conn, message_template_select_cv.format(id=cv_id))
    try:
        if len(cv_data) == 0:
            print("No vacancy with id =", cv_id)
            return None
        else:
            cv = CV(cv_data[0])
            return cv
    except:
        return CV()


# get random CV
def get_next_cv(conn):
    ids = execute_sql_query(conn, Queries.GET_CV_IDS)
    try:
        random_id = ids[random.randint(0, len(ids) - 1)][0]
    except:
        return CV()
    return get_cv(conn, random_id)


def post_like_from_vacancy(conn, vacancy_id, worker_id):
    execute_sql_query(conn, Queries.INSERT_LIKE_FROM_VACANCY.format(vacancy_id, worker_id))


def post_like_from_worker(conn, vacancy_id, worker_id):
    execute_sql_query(conn, Queries.INSERT_LIKE_FROM_WORKER.format(worker_id, vacancy_id))


def get_vacancy_categories(conn):
    categories = execute_sql_query(conn, Queries.GET_VACANCY_CATEGORIES)
    return utils.vocab_to_json(categories)


def get_liked_vacancies_ids(conn, user_id):
    message_template_get_liked_vacancies = Queries.GET_LIKED_VACANCIES_ID_BY_USERID
    return execute_sql_query(conn, message_template_get_liked_vacancies.format(id=user_id))


def get_liked_workers_ids(conn, employee_id):
    message_template_get_liked_workers = Queries.GET_LIKED_WORKERS_ID_BY_EMPLOYEEID
    return execute_sql_query(conn, message_template_get_liked_workers.format(id=employee_id))


def check_token(conn, log, token):
    res = execute_sql_query(conn, Queries.LOGIN_BY_LOG_AND_TOKEN.format(email=log, un_key=token))

    try:
        if len(res) > 0:
            return True
    except Exception:
        pass

    return False


def try_login(conn, log, password):
    sql = Queries.LOGIN_BY_LOG_AND_PASS
    res = execute_sql_query(conn, sql.format(email=log, passwd=password))
    token = ""

    try:
        if len(res) != 0:
            token = secrets.token_urlsafe(16)
            sql = Queries.PUSH_TOKEN_BY_LOG
            execute_sql_query(conn, sql.format(email=log, passwd=password, un_key=token), 'update')
    except:
        pass

    return token


def get_role(conn, email):
    res = execute_sql_query(conn, Queries.GET_USER_ROLE_BY_EMAIL.format(email_str=email))[0]
    return {"id": res[0], "name": res[1]}


def get_all_vacancy_preview(conn, email):
    result = execute_sql_query(conn, Queries.GET_VACANCY_PREVIEW_INFO.format(email_str=email))
    preview = {}
    try:
        for i in range(len(result)):
            vacancy = result[i]
            preview[vacancy[0]] = {
                "name": vacancy[1],
                "content": vacancy[2],
                "vac_cat": vacancy[3],
                "image_id": vacancy[4],
                "img_ext": vacancy[6],
            }
    except:
        pass

    return preview


def get_all_cv_preview(conn, email):
    result = execute_sql_query(conn, Queries.GET_CV_PREVIEW_INFO.format(email_str=email))
    preview = {}

    try:
        for i in range(len(result)):
            cv = result[cv[0]]
            preview[i] = {
                "name": cv[1],
                "content": cv[3],
                "salary": cv[2],
                "cv_cat": cv[4],
                "image_id": cv[5],
                "img_ext": cv[7],
            }
    except:
        pass

    return preview


def add_vacancy_to_db(conn, vac_name, vac_text, cr_date, vac_category):
    execute_sql_query(
        conn,
        Queries.ADD_VACANCY.format(vac_name, vac_text, cr_date, vac_category),
    )


def edit_vacancy_in_db(conn, vac_name, vac_text, cr_date, vac_category, id):
    execute_sql_query(
        conn,
        Queries.UPDATE_VACANCY.format(vac_name, vac_text, cr_date, vac_category, id),
    )


def add_cv_to_db(
    conn,
    cv_name: str,
    cv_text: str,
    experience_content: str,
    education_content: str,
    salary: int,
    cr_date: datetime.datetime,
    cv_category: int,
):
    execute_sql_query(
        conn,
        Queries.ADD_CV.format(cv_name, cv_text, experience_content, education_content, salary, cr_date, cv_category)
    )


def edit_cv_in_db(
    conn,
    cv_name: str,
    cv_text: str,
    experience_content: str,
    education_content: str,
    salary: int,
    cr_date: datetime.datetime,
    cv_category: int,
    id: int,
):
    execute_sql_query(
        conn,
        Queries.ADD_CV.format(cv_name, cv_text, experience_content, education_content, salary, cr_date, cv_category, id)
    )


def add_profile_to_db(
    conn,
    user_name,
    surname,
    age,
    gender,
):
    execute_sql_query(
        conn,
        Queries.ADD_PROFILE.format(
            user_name,
            surname,
            age,
            gender,
        )
    )


def edit_profile_in_db(
    conn,
    user_name,
    surname,
    age,
    gender,
    id,
):
    execute_sql_query(
        conn,
        Queries.UPDATE_PROFILE.format(
            user_name,
            surname,
            age,
            gender,
            id,
        )
    )