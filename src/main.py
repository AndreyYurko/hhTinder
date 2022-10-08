from requests import *
from db_connection import *


if __name__ == "__main__":
    tunnel, conn = connect_db()

    user = get_next_user(conn)
    print(user.id, user.name)

    close_ssh_tunnel(tunnel)
    close_connection(conn)

