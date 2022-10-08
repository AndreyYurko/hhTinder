import random
from user import *

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

def get_next_user(conn):
    ids = execute_sql_query(conn, "select id from users")
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    message_template_select_user = "select * from users where id = {id}"
    user_data = execute_sql_query(conn, message_template_select_user.format(id = random_id))
    user = User(user_data[0])
    return user
