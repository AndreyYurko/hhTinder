class Queries():
    GET_USER_IDS = "select id from users"
    GER_USER_BY_ID = "select * from users where id = {id}"

    GET_VACANCY_IDS = "select id from vacancy"
    GET_VACANCY_BY_ID = "select * from vacancy where id = {id}"

    GET_LIKED_VACANCIES_BY_USERID = "select vacancy_id from user_vac_match where user_id = {id}"

    GET_LIKED_WORKERS_BY_EMPLOYEEID = "select worker_id from employer_worker_match where employee_id = {id}"
