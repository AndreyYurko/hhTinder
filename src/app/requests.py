import datetime
import random
from models import *
from sql_queries import Queries
import utils, secrets


def execute_sql_query(conn, query, type='default'):
    records = None
    try:
        print("Executing SQL Query..")
        db_cursor = conn.cursor()
        db_cursor.execute(query)
        if type == 'default':
            records = db_cursor.fetchall()
        else:
            conn.commit()
        db_cursor.close()
        print("SUCCESS: Query was successful")
        return records
    except Exception as ex:
        print("FAILED: Wrong query")
        print(ex)


# get user by id
def get_user(conn, user_id):
    message_template_select_user = Queries.GET_USER_BY_ID
    user_data = execute_sql_query(conn, message_template_select_user.format(id=user_id))
    if len(user_data) == 0:
        print("No user with id =", user_id)
        return None
    else:
        user = User(user_data[0])
        return user


# get random user
def get_next_user(conn):
    ids = execute_sql_query(conn, Queries.GET_USER_IDS)
    random_id = ids[random.randint(0, len(ids) - 1)][0]
    return get_user(conn, random_id)


# get vacancy by id
def get_vacancy(conn, vac_id):
    message_template_select_vacancy = Queries.GET_VACANCY_BY_ID
    vacancy_data = execute_sql_query(conn, message_template_select_vacancy.format(id=vac_id))
    try:
        if len(vacancy_data) == 0:
            print("No vacancy with id =", vac_id)
            return None
        else:
            vacancy = Vacancy(vacancy_data[0])
            return vacancy
    except:
        return Vacancy()


# get random vacancy
def get_next_vacancy(conn):
    ids = execute_sql_query(conn, Queries.GET_VACANCY_IDS)
    try:
        random_id = ids[random.randint(0, len(ids) - 1)][0]
    except:
        return Vacancy()
    return get_vacancy(conn, random_id)

# set/update vacancy filters 
def set_vacancy_filters(conn, vac_id, vac_salary = None, vac_is_fulltime = None, vac_is_distant = None, vac_location_id = None, vac_grade_id = None):
    message_template_select_vacancy = Queries.GET_VACANCY_BY_ID_FROM_VACANCY_FILTERS
    vacancy = execute_sql_query(conn, message_template_select_vacancy.format(id=vac_id))
    if vacancy == []:
        message_template_insert_vacancy = Queries.INSERT_VACANCY_INTO_VACANCY_FILTERS
        execute_sql_query(conn, message_template_insert_vacancy.format(id=vac_id), "commit")

    if vac_salary != None:
        message_template_update_vacancy_salary = Queries.UPDATE_VACANCY_SALARY
        execute_sql_query(conn, message_template_update_vacancy_salary.format(salary=vac_salary, id=vac_id), "commit")
    if vac_is_fulltime != None:
        message_template_update_vacancy_is_fulltime = Queries.UPDATE_VACANCY_IS_FULLTIME
        execute_sql_query(conn, message_template_update_vacancy_is_fulltime.format(is_fulltime=vac_is_fulltime, id=vac_id), "commit")
    if vac_is_distant != None:
        message_template_update_vacancy_is_distant = Queries.UPDATE_VACANCY_IS_DISTANT
        execute_sql_query(conn, message_template_update_vacancy_is_distant.format(is_distant=vac_is_distant, id=vac_id), "commit")
    if vac_location_id != None:
        message_template_update_vacancy_location_id = Queries.UPDATE_VACANCY_LOCATION_ID
        execute_sql_query(conn, message_template_update_vacancy_location_id.format(location_id=vac_location_id, id=vac_id), "commit")
    if vac_grade_id != None:
        message_template_update_vacancy_grade_id = Queries.UPDATE_VACANCY_GRADE_ID
        execute_sql_query(conn, message_template_update_vacancy_grade_id.format(grade_id=vac_grade_id, id=vac_id), "commit")
    return


# get vacancy ids by filters
def get_vacancies_by_filters(conn, vac_salary = None, vac_is_fulltime = None, vac_is_distant = None, vac_location_id = None, vac_grade_id = None):
    message_template_select_vacancies_by_salary = Queries.GET_VACANCIES_BY_SALARY
    message_template_select_vacancies_by_is_fulltime = Queries.GET_VACANCIES_BY_IS_FULLTIME
    message_template_select_vacancies_by_is_distant = Queries.GET_VACANCIES_BY_IS_DISTANT
    message_template_select_vacancies_by_location_id = Queries.GET_VACANCIES_BY_LOCATION_ID
    message_template_select_vacancies_by_grade_id = Queries.GET_VACANCIES_BY_GRADE_ID

    message_template_select_vacancies = Queries.GET_VACANCY_IDS
    vacancies = execute_sql_query(conn, message_template_select_vacancies)
    vacancies_by_salary, vacancies_by_is_fulltime, vacancies_by_is_distant, vacancies_by_location_id, vacancies_by_grade_id = vacancies, vacancies, vacancies, vacancies, vacancies
    if (vac_salary != None):
        vacancies_by_salary = execute_sql_query(conn, message_template_select_vacancies_by_salary.format(salary=vac_salary))
    if (vac_is_fulltime != None):
        vacancies_by_is_fulltime = execute_sql_query(conn, message_template_select_vacancies_by_is_fulltime.format(is_fulltime=vac_is_fulltime))
    if (vac_is_distant != None): 
        vacancies_by_is_distant = execute_sql_query(conn, message_template_select_vacancies_by_is_distant.format(is_distant=vac_is_distant))
    if (vac_location_id != None):  
        vacancies_by_location_id = execute_sql_query(conn, message_template_select_vacancies_by_location_id.format(location_id=vac_location_id))
    if (vac_grade_id != None): 
        vacancies_by_grade_id = execute_sql_query(conn, message_template_select_vacancies_by_grade_id.format(grade_id=vac_grade_id))

    ans = list(set(vacancies) & set(vacancies_by_salary) & set(vacancies_by_is_fulltime) & set(vacancies_by_is_distant) & 
        set(vacancies_by_location_id) & set(vacancies_by_grade_id))
    ans = list(map(lambda item: item[0], ans))
    return ans


def set_users_filters(conn, user_id, user_salary=None, user_grade_id=None, user_languages_id=None):
    message_template_select_user = Queries.GET_USER_BY_ID_FROM_USER_FILTERS
    vacancy = execute_sql_query(conn, message_template_select_user.format(id=user_id))
    if vacancy == []:
        message_template_insert_user = Queries. INSERT_USER_INTO_USER_FILTERS
        execute_sql_query(conn, message_template_insert_user.format(id=user_id), "commit")

    if user_salary != None:
        message_template_update_user_salary = Queries.UPDATE_USER_SALARY
        execute_sql_query(conn, message_template_update_user_salary.format(salary=user_salary, id=user_id), "commit")
    if user_grade_id != None:
        message_template_update_user_grade_id = Queries.UPDATE_USER_GRADE_ID
        execute_sql_query(conn, message_template_update_user_grade_id.format(grade_id=user_grade_id, id=user_id),
                          "commit")
    if user_languages_id != None:
        message_template_update_user_language = Queries.UPDATE_USER_LANGUAGES
        execute_sql_query(conn, message_template_update_user_language.format(languages=user_languages_id ,id=user_id), "commit")

    return


# get user ids by filters
def get_users_by_filters(conn, user_salary=None, user_grade_id=None, user_languages_id=None):
    message_template_select_users_by_salary = Queries.GET_USERS_BY_SALARY_NOT_LESS_THAN
    message_template_select_users_by_grade_id = Queries.GET_USERS_BY_IS_GRADE_ID
    message_template_select_users_by_languages_id = Queries.GET_USERS_BY_LANGUAGES_ID

    message_template_select_vacancies = Queries.GET_USER_IDS
    vacancies = execute_sql_query(conn, message_template_select_vacancies)
    users_by_salary, users_by_grade_id, users_by_languages_id = vacancies, vacancies, vacancies
    if (user_salary != None):
        users_by_salary = execute_sql_query(conn, message_template_select_users_by_salary.format(salary_not_less_than=user_salary))
    if (user_grade_id != None):
        users_by_grade_id = execute_sql_query(conn, message_template_select_users_by_grade_id.format(grade_id=user_grade_id))
    if (user_languages_id != None):
        users_list = list()
        for i in range(0, len(user_languages_id)):
            users_by_languages_id = execute_sql_query(conn, message_template_select_users_by_languages_id.format(languages_id=user_languages_id[i]))
            users_list += list(users_by_languages_id)

    ans = list(set(vacancies) & set(users_by_salary) & set(users_by_grade_id) & set(users_list))
    ans = list(map(lambda item: item[0], ans))
    return ans

# get cv by id
def get_cv(conn, cv_id):
    message_template_select_cv = Queries.GET_CV_BY_ID
    cv_data = execute_sql_query(conn, message_template_select_cv.format(id=cv_id))
    try:
        if len(cv_data) == 0:
            print("No vacancy with id =", cv_id)
            return None
        else:
            cv = CV(cv_data[0])
            return cv
    except:
        return CV()


# get random CV
def get_next_cv(conn):
    ids = execute_sql_query(conn, Queries.GET_CV_IDS)
    try:
        random_id = ids[random.randint(0, len(ids) - 1)][0]
    except:
        return CV()
    return get_cv(conn, random_id)


def post_like_from_vacancy(conn, vacancy_id, worker_id):
    execute_sql_query(conn, Queries.INSERT_LIKE_FROM_VACANCY.format(vacancy_id, worker_id))


def post_like_from_worker(conn, vacancy_id, worker_id):
    execute_sql_query(conn, Queries.INSERT_LIKE_FROM_WORKER.format(worker_id, vacancy_id))


def get_vacancy_categories(conn):
    categories = execute_sql_query(conn, Queries.GET_VACANCY_CATEGORIES)
    return utils.vocab_to_json(categories)


def get_liked_vacancies_ids(conn, user_id):
    message_template_get_liked_vacancies = Queries.GET_LIKED_VACANCIES_ID_BY_USERID
    return execute_sql_query(conn, message_template_get_liked_vacancies.format(id=user_id))


def get_liked_workers_ids(conn, employee_id):
    message_template_get_liked_workers = Queries.GET_LIKED_WORKERS_ID_BY_EMPLOYEEID
    return execute_sql_query(conn, message_template_get_liked_workers.format(id=employee_id))


def check_token(conn, log, token):
    res = execute_sql_query(conn, Queries.LOGIN_BY_LOG_AND_TOKEN.format(email=log, un_key=token))

    try:
        if len(res) > 0:
            return True
    except Exception:
        pass

    return False


def try_login(conn, log, password):
    sql = Queries.LOGIN_BY_LOG_AND_PASS
    res = execute_sql_query(conn, sql.format(email=log, passwd=password))
    token = ""

    try:
        if len(res) != 0:
            token = secrets.token_urlsafe(16)
            sql = Queries.PUSH_TOKEN_BY_LOG
            execute_sql_query(conn, sql.format(email=log, passwd=password, un_key=token), 'update')
    except:
        pass

    return token


def get_role(conn, email):
    res = execute_sql_query(conn, Queries.GET_USER_ROLE_BY_EMAIL.format(email_str=email))[0]
    return {"id": res[0], "name": res[1]}


def get_all_vacancy_preview(conn, email):
    result = execute_sql_query(conn, Queries.GET_VACANCY_PREVIEW_INFO.format(email_str=email))
    preview = {}
    try:
        for i in range(len(result)):
            vacancy = result[i]
            preview[vacancy[0]] = {
                "name": vacancy[1],
                "content": vacancy[2],
                "vac_cat": vacancy[3],
                "image_id": vacancy[4],
                "img_ext": vacancy[6],
            }
    except:
        pass

    return preview


def get_all_cv_preview(conn, email):
    result = execute_sql_query(conn, Queries.GET_CV_PREVIEW_INFO.format(email_str=email))
    preview = {}

    try:
        for i in range(len(result)):
            cv = result[cv[0]]
            preview[i] = {
                "name": cv[1],
                "content": cv[3],
                "salary": cv[2],
                "cv_cat": cv[4],
                "image_id": cv[5],
                "img_ext": cv[7],
            }
    except:
        pass

    return preview


def add_vacancy_to_db(conn, vac_name, vac_text, cr_date, vac_category):
    execute_sql_query(
        conn,
        Queries.ADD_VACANCY.format(vac_name, vac_text, cr_date, vac_category),
    )


def edit_vacancy_in_db(conn, vac_name, vac_text, cr_date, vac_category, id):
    execute_sql_query(
        conn,
        Queries.UPDATE_VACANCY.format(vac_name, vac_text, cr_date, vac_category, id),
    )


def add_cv_to_db(
    conn,
    cv_name: str,
    cv_text: str,
    experience_content: str,
    education_content: str,
    salary: int,
    cr_date: datetime.datetime,
    cv_category: int,
):
    execute_sql_query(
        conn,
        Queries.ADD_CV.format(cv_name, cv_text, experience_content, education_content, salary, cr_date, cv_category)
    )


def edit_cv_in_db(
    conn,
    cv_name: str,
    cv_text: str,
    experience_content: str,
    education_content: str,
    salary: int,
    cr_date: datetime.datetime,
    cv_category: int,
    id: int,
):
    execute_sql_query(
        conn,
        Queries.ADD_CV.format(cv_name, cv_text, experience_content, education_content, salary, cr_date, cv_category, id)
    )


def add_profile_to_db(
    conn,
    user_name,
    surname,
    age,
    gender,
):
    execute_sql_query(
        conn,
        Queries.ADD_PROFILE.format(
            user_name,
            surname,
            age,
            gender,
        )
    )


def edit_profile_in_db(
    conn,
    user_name,
    surname,
    age,
    gender,
    id,
):
    execute_sql_query(
        conn,
        Queries.UPDATE_PROFILE.format(
            user_name,
            surname,
            age,
            gender,
            id,
        )
    )

# get user ids with whom the given vacancy has a match
def get_matches_for_vacancy(conn, vac_id):
    message_template_select_matches = Queries.GET_MATCHES_FOR_VACANCY
    user_ids = execute_sql_query(conn, message_template_select_matches.format(id=vac_id))
    user_ids = list(map(lambda item: item[0], user_ids))
    return user_ids

# get vacancy ids with whom the given user has a match
def get_matches_for_user(conn, user_id):
    message_template_select_matches = Queries.GET_MATCHES_FOR_USER
    vacancy_ids = execute_sql_query(conn, message_template_select_matches.format(id=user_id))
    vacancy_ids = list(map(lambda item: item[0], vacancy_ids))
    return vacancy_ids

# insert match between user_id and vacancy_id if it's not there (if there is a match, do nothing)
def insert_match(conn, user_id, vacancy_id):
    vacancy_ids_for_current_user = get_matches_for_user(conn, user_id)
    if vacancy_id in vacancy_ids_for_current_user:
        return
    else:
        message_template_insert_match = Queries.INSERT_MATCH
        execute_sql_query(conn, message_template_insert_match.format(user_id=user_id, vacancy_id=vacancy_id), "commit")

def get_language_by_id(conn, lang_id):
    message_template_get_language_name = Queries.GET_LANGUAGE_BY_ID
    name = execute_sql_query(conn, message_template_get_language_name.format(id=lang_id))
    return name[0][0]

def get_grade_by_id(conn, grade_id):
    message_template_get_grade_name = Queries.GET_GRADE_BY_ID
    name = execute_sql_query(conn, message_template_get_grade_name.format(id=grade_id))
    return name[0][0]

def get_gender_by_id(conn, gender_id):
    message_template_get_gender_name = Queries.GET_GENDER_BY_ID
    name = execute_sql_query(conn, message_template_get_gender_name.format(id=gender_id))
    return name[0][0]

def get_location_by_id(conn, location_id):
    message_template_get_location_name = Queries.GET_LOCATION_BY_ID
    name = execute_sql_query(conn, message_template_get_location_name.format(id=location_id))
    return name[0][0]

def get_job_category_by_id(conn, job_category_id):
    message_template_get_location_name = Queries.GET_JOB_CATEGORY_BY_ID
    name = execute_sql_query(conn, message_template_get_location_name.format(id=job_category_id))
    return name[0][0]

