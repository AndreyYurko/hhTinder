from typing import Callable
from prometheus_fastapi_instrumentator.metrics import Info
from prometheus_client import Counter
from prometheus_fastapi_instrumentator import Instrumentator


def information_about_all_requests() -> Callable[[Info], None]:
    request_base_hello_world = Counter('Hello_World', 'Output "Hello World"')
    read_item_vacancy = Counter('Read_item', 'Number of read_item')
    next_worker = Counter('Next_worker', 'Number of swipes workers')
    next_vacancy = Counter('Next_vacancy', 'Number os swipes vacancy')
    cv_request = Counter('Request_of_cv', 'Number of cv requests')
    vacancy_request = Counter('Number_of_vacancy', 'Number of requests of vacancy')
    history_vacancy = Counter('Liked_workers', 'Number of liked vacancy')
    history_worker = Counter('Liked_vacancy', 'Number of liked workers')
    worker_like = Counter('Like_from_worker', 'Number of vacancy likes')
    vacancy_like = Counter('Like_from_vacancy', 'Number of likes from Vacancy')
    vacancy_categories = Counter('Vacancy_categories', 'Number of vacancies categories')
    login_token = Counter('Login_with_token', 'Number of login by token')
    login_password = Counter('Login_with_password', 'Number of login by password')
    user_role_email = Counter('User_role_by_email', 'Number of users role email')
    vacancy_preview_info_email = Counter('Get_vacancy_preview', 'Number of getting vacancy preview')
    cv_preview_info_email = Counter('Get_cv_preview', 'Number of getting cv preview')
    add_vacancy = Counter('Add_vacancy', 'Number of adding vacancy')
    edit_vacancy = Counter('Edit_vacancy', 'Number of editing vacancy')
    add_cv = Counter('Add_cv', 'Number of adding cv')
    edit_cv = Counter('Edit_cv', 'Number of editing cv')
    add_profile = Counter('Add_profile', 'Number of adding profile')
    edit_profile = Counter('Edit_profile', 'Number of editing profile')

    def instrumentation(info: Info) -> None:
        if info.modified_handler == "/":
            request_base_hello_world.inc()
        elif info.modified_handler == "/items/{item_id}":
            read_item_vacancy.inc()
        elif info.modified_handler == "/next_worker/":
            next_worker.inc()
        elif info.modified_handler == "/next_vacancy/":
            next_vacancy.inc()
        elif info.modified_handler == "/cv/{cv_id}":
            cv_request.inc()  #
        elif info.modified_handler == "/vacancy/{vac_id}":
            vacancy_request.inc()  #
        elif info.modified_handler == "/history/vacancy/{job_id}":
            history_vacancy.inc()
        elif info.modified_handler == "/history/worker/{worker_id}":
            history_worker.inc()  #
        elif info.modified_handler == "/worker_like/":
            worker_like.inc()  #
        elif info.modified_handler == "/vacancy_like/":
            vacancy_like.inc()
        elif info.modified_handler == "/vacancy/vacancy_categories":
            vacancy_categories.inc()
        elif info.modified_handler == "/login/token/{login}/{token}":
            login_token.inc()
        elif info.modified_handler == "/login/password/{login}/{password}":
            login_password.inc()
        elif info.modified_handler == "/user_role/{email}":
            user_role_email.inc()
        elif info.modified_handler == "/vacancy_preview_info/{email}":
            vacancy_preview_info_email.inc()
        elif info.modified_handler == "/cv_preview_info/{email}":
            cv_preview_info_email.inc()
        elif info.modified_handler == "/add_vacancy/":
            add_vacancy.inc()
        elif info.modified_handler == "/edit_vacancy/":
            edit_vacancy.inc()
        elif info.modified_handler == "/add_cv/":
            add_cv.inc()
        elif info.modified_handler == "/edit_cv/":
            edit_cv.inc()
        elif info.modified_handler == "/add_profile/":
            add_profile.inc()
        elif info.modified_handler == "/edit_profile/":
            edit_profile.inc()
    return instrumentation


instrumentator = Instrumentator(
        should_group_status_codes=False,
        should_ignore_untemplated=True,
        should_respect_env_var=False,
        should_instrument_requests_inprogress=False,
        excluded_handlers=[".*admin.*", "/metrics"],
        # inprogress_name="inprogress",
        inprogress_labels=True,
        ).add(information_about_all_requests())
