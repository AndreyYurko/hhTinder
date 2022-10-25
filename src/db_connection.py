import psycopg2
from sshtunnel import SSHTunnelForwarder
import json


def connect_db():
    with open('config.json') as f:
        templates = json.load(f)
    ssh_host = templates["ssh_host"]
    ssh_password = templates["ssh_password"]
    ssh_username = templates["ssh_username"]
    database_name = templates["database_name"]
    user_name = templates["user_name"]
    try:

        local_port = 5432
        local_host = 'localhost'
        is_server = True
        tunnel = None


        if ssh_host != "":
            tunnel = SSHTunnelForwarder(
                (ssh_host, 22),
                ssh_username="root",
                ssh_password=ssh_password,
                remote_bind_address=('localhost', 5432))

            tunnel.start()
            local_port = tunnel.local_bind_port
            is_server = False

            print("SUCCESS: Server connected")

        params = {
            'database': database_name,
            'user': user_name,
            'password': ssh_password,
            'host': local_host,
            'port': local_port
        }
        conn = psycopg2.connect(**params)
        print("SUCCESS: Database connected")
        return tunnel, conn
    except:
        print("FAILED: Connection failed")


def close_ssh_tunnel(tunnel):
    if tunnel is not None:
        tunnel.close


def close_connection(conn):
    conn.close()
