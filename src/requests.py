import random
from models import *

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
    ids = execute_sql_query(conn, "select id from users")
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    message_template_select_user = "select * from users where id = {id}"
    user_data = execute_sql_query(conn, message_template_select_user.format(id = random_id))
    user = User(user_data[0])
    return user

# get random vacancy
def get_next_vacancy(conn):
    ids = execute_sql_query(conn, "select id from vacancy")
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    message_template_select_vacancy = "select * from vacancy where id = {id}"
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

def get_liked_workers(job_id):
    # это уже второстепенная задача, сперва надо создать таблицы для лайков от работников к работам и наоборот
    return None