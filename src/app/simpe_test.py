# from requests import *
# from db_connection import *
# import asyncio
#
# # симпл сервер тест
#
# async def start():
#     tunnel, conn = connect_db()
#
#     user = get_next_user(conn)
#     print(user.id, user.name)
#
#     close_ssh_tunnel(tunnel)
#     close_connection(conn)
# if __name__ == "__main__":
#     loop = asyncio.get_event_loop()
#     loop.run_until_complete(start())
#     loop.close()
#     print("HELLO WORLD")

from requests import *
from db_connection import *
import asyncio

# симпл сервер тест

async def start():
    tunnel, conn = connect_db()

    # ids = get_users_by_filters(conn, user_salary=200000, user_languages_id=[6, 4, 8, 1])
    ids = set_users_filters(conn, 50, 30000, 2, user_languages_id={7, 9})
    print(ids)

    # user = get_next_user(conn)
    # print(user.id, user.name)

    close_ssh_tunnel(tunnel)
    close_connection(conn)

if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(start())
    loop.close()
    print("HELLO WORLD")