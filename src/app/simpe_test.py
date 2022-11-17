from requests import *
from db_connection import *
import asyncio

# симпл сервер тест

async def start():
    tunnel, conn = connect_db()

    user = get_next_user(conn)
    print(user.id, user.name)

    close_ssh_tunnel(tunnel)
    close_connection(conn)
if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.run_until_complete(start())
    loop.close()
    print("HELLO WORLD")
