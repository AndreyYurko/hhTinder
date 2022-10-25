import random
from models import *
from sql_queries import Queries

def execute_sql_query(conn, query):
    try:
        print("Executing SQL Query..")
        print(query)
        db_cursor = conn.cursor()
        db_cursor.execute(query)
        records = db_cursor.fetchall()
        db_cursor.close()
        print("SUCCESS: Query was successful")
        return records
    except:
        print("FAILED: Query failed")

# get random user
def get_next_user(conn):
    ids = execute_sql_query(conn, Queries.GET_USER_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    message_template_select_user = Queries.GER_USER_BY_ID
    user_data = execute_sql_query(conn, message_template_select_user.format(id = random_id))
    user = User(user_data[0])
    return user

# get random vacancy
def get_next_vacancy(conn):
    ids = execute_sql_query(conn, Queries.GET_VACANCY_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    message_template_select_vacancy = Queries.GET_VACANCY_BY_ID
    vacancy_data = execute_sql_query(conn, message_template_select_vacancy.format(id = random_id))
    vacancy = Vacancy(vacancy_data[0])
    return vacancy

def post_like_from_job(job_id, worker_id):
    # Здесь точно так же надо написать sql запрос в бд который бы поставил лайк в соответствующую таблицу,
    # пока что здесь будет стоять заглушка
    return True

def post_like_from_worker(job_id, worker_id):
    # аналогично
    return True

def get_liked_vacancies(conn, user_id):
    message_template_get_liked_vacancies = Queries.GET_LIKED_VACANCIES_BY_USERID
    vacancies = execute_sql_query(conn, message_template_get_liked_vacancies.format(id = user_id))
    print(vacancies)
    # TODO
    return None

def get_liked_workers(conn, employee_id):
    message_template_get_liked_workers = Queries.GET_LIKED_WORKERS_BY_EMPLOYEEID
    workers = execute_sql_query(conn, message_template_get_liked_workers.format(id = employee_id))
    print(workers)
    # TODO
    return None