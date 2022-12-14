class Queries():
    GET_USER_IDS = """
        SELECT id from users
    """
    GET_USER_BY_ID = """
        SELECT * FROM users WHERE id = {id}
    """

    GET_VACANCY_IDS = """
        SELECT id FROM vacancy
    """

    GET_VACANCY_BY_ID = """
        SELECT * FROM vacancy WHERE id = {id}
    """

    GET_VACANCY_BY_ID_FROM_VACANCY_FILTERS = """
        SELECT vacancy_id FROM vacancy_filters WHERE
            vacancy_id = {id}
    """

    INSERT_VACANCY_INTO_VACANCY_FILTERS = """
        INSERT INTO vacancy_filters(vacancy_id)
        VALUES ({id})
    """

    INSERT_USER_INTO_USER_FILTERS = """
            INSERT INTO user_filters(user_id)
            VALUES ({id})
        """

    UPDATE_VACANCY_SALARY = """
        UPDATE vacancy_filters SET salary={salary} 
        WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_IS_FULLTIME = """
        UPDATE vacancy_filters SET is_fulltime={is_fulltime} 
        WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_IS_DISTANT = """
        UPDATE vacancy_filters SET is_distant={is_distant} 
        WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_LOCATION_ID = """
        UPDATE vacancy_filters SET location_id={location_id} 
        WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_GRADE_ID = """
        UPDATE vacancy_filters SET grade_id={grade_id} 
        WHERE user_id={id}
    """

    UPDATE_USER_SALARY = """
            UPDATE user_filters SET salary_not_less_than={salary} 
            WHERE user_id={id}
        """

    UPDATE_USER_GRADE_ID = """
        UPDATE user_filters SET grade_id={grade_id} 
        WHERE user_id={id}
    """

    UPDATE_USER_LANGUAGES = """
        UPDATE user_filters SET languages_id='{languages}'
        WHERE user_id={id}
    """

    GET_USER_BY_ID_FROM_USER_FILTERS = """
           SELECT user_id FROM user_filters WHERE
               user_id = {id}
       """

    GET_VACANCIES_BY_SALARY = """
        SELECT vacancy_id FROM vacancy_filters WHERE 
            salary >= {salary}
    """

    GET_VACANCIES_BY_IS_FULLTIME = """
        SELECT vacancy_id FROM vacancy_filters WHERE 
            is_fulltime = {is_fulltime}
    """

    GET_VACANCIES_BY_IS_DISTANT = """
        SELECT vacancy_id FROM vacancy_filters WHERE 
            is_distant = {is_distant}
    """

    GET_VACANCIES_BY_LOCATION_ID = """
        SELECT vacancy_id FROM vacancy_filters WHERE 
            location_id = {location_id}
    """

    GET_VACANCIES_BY_GRADE_ID = """
        SELECT vacancy_id FROM vacancy_filters WHERE 
            grade_id = {grade_id}
    """

    GET_USERS_BY_SALARY_NOT_LESS_THAN = """
            SELECT user_id FROM user_filters WHERE 
                salary_not_less_than <= {salary_not_less_than}
        """

    GET_USERS_BY_IS_GRADE_ID = """
            SELECT user_id FROM user_filters WHERE 
                grade_id = {grade_id}
        """

    GET_USERS_BY_LANGUAGES_ID = """
            SELECT user_id FROM user_filters WHERE 
                '{languages_id}' = ANY(languages_id) 
        """

    GET_LIKED_VACANCIES_ID_BY_USERID = """
        SELECT vacancy_id 
        FROM user_vac_match 
        WHERE user_id = {id}
    """

    GET_LIKED_WORKERS_ID_BY_EMPLOYEEID = """
        SELECT worker_id 
        FROM employer_worker_match 
        WHERE employee_id = {id}
    """

    GET_CV_IDS = """
        SELECT  cv.id 
        FROM cv 
            JOIN user_info on cv.cr_user = user_info.cr_user 
            JOIN images on user_info.img_id = images.id 
            JOIN voc_gender on user_info.gender_id = voc_gender.id 
            JOIN voc_job_category on cv_category = voc_job_category.id
    """
    GET_CV_BY_ID = """
        SELECT  cv.id as vac_id, cv_name, cv_text, salary, experience_content, education_content,ui.name, surname, age, i.id as image_id, extension, vg.name as gender, vjc.name as category 
        FROM cv 
            JOIN user_info on cv.cr_user = user_info.cr_user 
            JOIN images on user_info.img_id = images.id 
            JOIN voc_gender on user_info.gender_id = voc_gender.id 
            JOIN voc_job_category on cv_category = voc_job_category.id 
        WHERE cv.id = {id}
    """

    INSERT_LIKE_FROM_VACANCY = """
        INSERT INTO employer_worker_match 
        VALUES ({vacancy_id}, {user_id})
    """
    INSERT_LIKE_FROM_WORKER = """
        INSERT INTO user_vac_match 
        VALUES ({user_id}, {vacancy_id})
    """

    GET_VACANCY_CATEGORIES = """
        SELECT * 
        FROM voc_job_category
    """

    LOGIN_BY_LOG_AND_PASS = """
        SELECT * 
        FROM users 
        WHERE email = '{email}' AND passwd = '{passwd}' limit 1
    """
    LOGIN_BY_LOG_AND_TOKEN = """
        SELECT * 
        FROM users 
        WHERE email = '{email}' AND un_key = '{un_key}' limit 1
    """

    PUSH_TOKEN_BY_LOG = """
        UPDATE users 
        SET un_key = '{un_key}' 
        WHERE (email = '{email}' AND passwd = '{passwd}')
    """

    GET_USER_ROLE_BY_EMAIL = """
        SELECT role_id, roles.name 
        FROM users 
            JOIN users_role roles on users.role_id = roles.id 
        WHERE users.email = '{email_str}'
    """

    GET_VACANCY_PREVIEW_INFO = """
        SELECT vacancy.id, vac_name, vac_text, vac_category, img_id, images.name, images.extension 
        FROM vacancy 
            LEFT JOIN users on vacancy.cr_user = users.id 
            LEFT JOIN images on vacancy.img_id = images.id 
        WHERE users.email = '{email_str}'
    """
    GET_CV_PREVIEW_INFO = """
        SELECT cv.id, cv_name, salary, cv_text, cv_category, img_id, images.name, images.extension 
        FROM cv 
            LEFT JOIN users on cv.cr_user = users.id 
            LEFT JOIN images on cv.img_id = images.id 
        WHERE users.email = '{email_str}'
    """

    ADD_VACANCY = """
        INSERT INTO vacancy(vac_name, vac_text, vac_category, cr_user)
        VALUES
        ('{vac_name}', '{vac_text}', {vac_category}, {cr_user})
    """
    UPDATE_VACANCY = """
        UPDATE vacancy
        SET vac_name='{vac_name}', vac_text='{vac_text}', vac_category={vac_category}
        WHERE id = {id} 
    """

    ADD_CV = """
        INSERT INTO cv(cv_name, cv_text, experience_content, education_content, salary, cv_category, cr_user)
        VALUES
        ('{cv_name}', '{cv_text}', '{experience_content}', '{education_content}', {salary}, {cv_category}, {cr_user})
    """
    UPDATE_CV = """
        UPDATE cv
        SET cv_name='{cv_name}', cv_text='{cv_text}', experience_content='{experience_content}', education_content='{education_content}', salary={salary}, cv_category={cv_category}
        WHERE id = {id} 
    """

    ADD_PROFILE = """
        INSERT INTO user_info(user_name, surname, age, gender_id)
        VALUES 
        ('{user_name}', '{surname}', {age}, {gender_id})
    """

    UPDATE_PROFILE = "UPDATE user_info SET name='{user_name}', surname='{surname}', age={age}, gender_id={gender_id} WHERE id = {id}"

    GET_MATCHES_FOR_VACANCY = """
        SELECT user_id from matches WHERE vacancy_id = {id}
    """

    GET_MATCHES_FOR_USER = """
        SELECT vacancy_id from matches WHERE user_id = {id}
    """

    INSERT_MATCH = """
        INSERT INTO matches(user_id, vacancy_id)
        VALUES ({user_id}, {vacancy_id})
    """

    GET_LANGUAGE_BY_ID = """
        SELECT name from voc_languages
        WHERE id = {id}
    """

    GET_GRADE_BY_ID = """
        SELECT name from voc_grade
        WHERE id = {id}
    """

    GET_GENDER_BY_ID = """
        SELECT name from voc_gender
        WHERE id = {id}
    """

    GET_LOCATION_BY_ID = """
        SELECT name from voc_locations
        WHERE id = {id}
    """

    GET_JOB_CATEGORY_BY_ID = """
        SELECT name from voc_job_category
        WHERE id = {id}
    """

    GET_PROFILES = """
    select id, email from users
    """

    GET_PROFILES_BY_ID = """
select users.id,
       email,
       users.name,
       passwd,
       ui.name,
       ui.surname,
       age,
       gender_id,
       role_id,
       i.id,
       i.extension,
       ui.cr_user,
       ui.id as info_id

from users
         join user_info ui on users.id = ui.cr_user join images i on ui.img_id = i.id
where users.id = {user_id}

    """

    GET_PROFILES_BY_EMAIL = """
    select users.id,
           email,
           users.name,
           passwd,
           ui.name,
           ui.surname,
           age,
           gender_id,
           role_id,
           i.id,
           i.extension,
           ui.cr_user,
           ui.id as info_id

    from users
             join user_info ui on users.id = ui.cr_user join images i on ui.img_id = i.id
    where email = '{login}'
        """


    ADD_USER = """
            INSERT INTO users(email, passwd, name, role_id)
            VALUES 
            ({email}, {passwd}, {name}, {role_id})
        """

    DELETE_CV = "DELETE FROM cv WHERE id = {id}"

    DELETE_VACANCY = "DELETE FROM vacancy WHERE id = {id}"