import random
from models import *
from sql_queries import Queries
import utils, secrets


def execute_sql_query(conn, query, type='default'):
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
    except:
        print("FAILED: Query failed")


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
    if len(vacancy_data) == 0:
        print("No vacancy with id =", vac_id)
        return None
    else:
        vacancy = Vacancy(vacancy_data[0])
        return vacancy


# get random vacancy
def get_next_vacancy(conn):
    ids = execute_sql_query(conn, Queries.GET_VACANCY_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    return get_vacancy(conn, random_id)


# get cv by id
def get_cv(conn, cv_id):
    message_template_select_cv = Queries.GET_CV_BY_ID
    cv_data = execute_sql_query(conn, message_template_select_cv.format(id=cv_id))
    if len(cv_data) == 0:
        print("No vacancy with id =", cv_id)
        return None
    else:
        cv = CV(cv_data[0])
        return cv


# get random CV
def get_next_cv(conn):
    ids = execute_sql_query(conn, Queries.GET_CV_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    return get_cv(conn, random_id)


def post_like_from_job(conn, job_id, worker_id):
    message_template_post_like_from_job = Queries.INSERT_LIKE_FROM_VACANCY
    execute_sql_query(conn, message_template_post_like_from_job.format(job_id, worker_id))


def post_like_from_worker(conn, job_id, worker_id):
    message_template_post_like_from_worker = Queries.INSERT_LIKE_FROM_WORKER
    execute_sql_query(conn, message_template_post_like_from_worker.format(worker_id, job_id))


def get_job_categories(conn):
    categories = execute_sql_query(conn, Queries.GET_JOB_CATEGORIES)
    return utils.vocab_to_json(categories)


def get_liked_vacancies_ids(conn, user_id):
    message_template_get_liked_vacancies = Queries.GET_LIKED_VACANCIES_ID_BY_USERID
    vacancies = execute_sql_query(conn, message_template_get_liked_vacancies.format(id=user_id))
    # TODO
    return vacancies


def get_liked_workers_ids(conn, employee_id):
    message_template_get_liked_workers = Queries.GET_LIKED_WORKERS_ID_BY_EMPLOYEEID
    workers = execute_sql_query(conn, message_template_get_liked_workers.format(id=employee_id))
    # TODO
    return workers


def check_token(conn, log, token):
    sql = Queries.LOGIN_BY_LOG_AND_TOKEN
    res = execute_sql_query(conn, sql.format(email=log, un_key=token))

    try:
        if (len(res) > 0):
            return True
    except:
        pass

    return False


def try_login(conn, log, password):
    sql = Queries.LOGIN_BY_LOG_AND_PASS
    res = execute_sql_query(conn, sql.format(email=log, passwd=password))
    token = ""

    if (len(res) != 0):
        token = secrets.token_urlsafe(16)
        sql = Queries.PUSH_TOKEN_BY_LOG
        res = execute_sql_query(conn, sql.format(email=log, passwd=password, un_key=token), 'update')

    return token


def get_role(conn, email):
    return execute_sql_query(conn, Queries.GET_USER_ROLE_BY_EMAIL.format(email_str=email))


def get_all_vacancy_preview(conn, email):
    res = execute_sql_query(conn, Queries.GET_VACANCY_PREVIEW_INFO.format(email_str=email))
    out = {}
    for i in range(len(res)):
        el = res[i]
        jsonObj = {"name": el[0], "content": el[1], "vac_cat": el[2], "image_id": el[3], "img_ext": el[5]}
        out[i] = jsonObj

    return out


def get_all_cv_preview(conn, email):
    res = execute_sql_query(conn, Queries.GET_CV_PREVIEW_INFO.format(email_str=email))
    out = {}

    for i in range(len(res)):
        el = res[i]
        jsonObj = {"name": el[0], "content": el[2], "salary":el[1], "cv_cat": el[3], "image_id": el[4], "img_ext": el[6]}
        out[i] = jsonObj

    return out
