from typing import Callable
from prometheus_fastapi_instrumentator.metrics import Info
from prometheus_client import Counter
from prometheus_fastapi_instrumentator import Instrumentator

def information_about_all_requests() -> Callable[[Info], None]:
    request_base_hello_world = Counter('Hello_World', 'Output "Hello World"')
    read_item_vacancy = Counter('Read_item', 'Number of read_item4')
    next_worker_state = Counter('Next_worker', 'Number of getting next user')
    next_worker_vacancy = Counter('Next_vacancy', 'Number of getting next vacancy')
    liked_workers_history = Counter('History', 'Number of getting history')
    liked_vacancy = Counter('Liked_vacancy', 'Number of getting vacancy')
    worker_like = Counter('Vacancy_like', 'Number of vacancy likes')
    job_like = Counter('Worker_like', 'Number of worker likes')
    vacancy_request = Counter('Number_of_vacancy', 'Number of requests of vacancy')
    cv_request = Counter('Request_of_cv', 'Number of cv requests')
    def instrumentation(info: Info) -> None:
        if info.modified_handler == "/":
            request_base_hello_world.inc()
        elif info.modified_handler == "/items/{item_id}":
            read_item_vacancy.inc()
        elif info.modified_handler == "/worker/{job_id}":
            next_worker_state.inc()
        elif info.modified_handler == "/job/{worker_id}":
            next_worker_vacancy.inc()
        elif info.modified_handler == "/history/job/{job_id}":
            liked_workers_history.inc()
        elif info.modified_handler == "/history/worker/{worker_id}":
            liked_vacancy.inc()
        elif info.modified_handler == "/worker_like/":
            worker_like.inc()
        elif info.modified_handler == "/job_like/":
            job_like.inc()
        elif info.modified_handler == "/vacancy/{vac_id}":
            vacancy_request.inc()
        elif info.modified_handler == "/cv/{cv_id}":
            cv_request.inc()
    return instrumentation

instrumentator = Instrumentator(
        should_group_status_codes=False,
        should_ignore_untemplated=True,
        should_respect_env_var=False,
        should_instrument_requests_inprogress=True,
        excluded_handlers=[".*admin.*", "/metrics"],
        inprogress_name="inprogress",
        inprogress_labels=True,
        ).add(information_about_all_requests())
