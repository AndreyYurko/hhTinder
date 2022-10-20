from requests import *
from db_connection import *
from typing import Union
import uvicorn
from fastapi import FastAPI

app = FastAPI()  # rest api
app.type = "00"
tunnel_, conn_ = connect_db()
app.state.connection = conn_


# def add_smthng(smthng):
#     global connection
#     connection += [smthng]
#     return True

# tunnel, conn = connect_db() # server for DB

@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}


@app.get("/worker/{job_id}")
def next_worker(job_id: int):
    # global connection
    print(job_id)
    user = get_next_user(app.state.connection)
    print(user.id, user.name, job_id)
    return user.to_json()


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
    uvicorn.run("main:app", host="127.0.0.1", port=8000, log_level="info")
    print("ehm")


if __name__ == '__main__':
    Start()
# print("Something is not quite right")
# close_ssh_tunnel(tunnel)
# close_connection(conn)
