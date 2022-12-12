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

    UPDATE_VACANCY_SALARY = """
        UPDATE vacancy_filters SET salary={salary} WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_IS_FULLTIME = """
        UPDATE vacancy_filters SET is_fulltime={is_fulltime} WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_IS_DISTANT = """
        UPDATE vacancy_filters SET is_distant={is_distant} WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_LOCATION_ID = """
        UPDATE vacancy_filters SET location_id={location_id} WHERE vacancy_id={id}
    """

    UPDATE_VACANCY_GRADE_ID = """
        UPDATE vacancy_filters SET grade_id={grade_id} WHERE vacancy_id={id}
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
        WHERE email LIKE '{email}' AND passwd LIKE '{passwd}' limit 1
    """
    LOGIN_BY_LOG_AND_TOKEN = """
        SELECT * 
        FROM users 
        WHERE email LIKE '{email}' AND un_key LIKE '{un_key}' limit 1
    """

    PUSH_TOKEN_BY_LOG = """
        UPDATE users 
        SET un_key = '{un_key}' 
        WHERE (email LIKE '{email}' AND passwd LIKE '{passwd}')
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
            JOIN users on vacancy.cr_user = users.id 
            JOIN images on vacancy.img_id = images.id 
        WHERE users.email = '{email_str}'
    """
    GET_CV_PREVIEW_INFO = """
        SELECT cv.id, cv_name, salary, cv_text, cv_category, img_id, images.name, images.extension 
        FROM cv 
            JOIN users on cv.cr_user = users.id 
            JOIN images on cv.img_id = images.id 
        WHERE users.email = '{email_str}'
    """

    ADD_VACANCY = """
        INSERT INTO vacancy(vac_name, vac_text, cr_date, vac_category)
        VALUES
        ({vac_name}. {vac_text}, {cr_date}, {vac_category})
    """
    UPDATE_VACANCY = """
        UPDATE vacancy
        SET {vac_name}, {vac_text}, {cr_date}, {vac_category}
        WHERE id = {id} 
    """

    ADD_CV = """
        INSERT INTO cv(cv_name, cv_text, experience_content, education_content, salary, cr_date, cv_category)
        VALUES
        ({cv_name}, {cv_text}, {experience_content}, {education_content}, {salary}, {cr_date}, {cv_category})
    """
    UPDATE_CV = """
        UPDATE cv
        SET {cv_name}, {cv_text}, {experience_content}, {education_content}, {salary}, {cr_date}, {cv_category}
        WHERE id = {id} 
    """

    ADD_PROFILE = """
        INSERT INTO user_info(user_name, surname, age, gender_id)
        VALUES 
        ({user_name}, {surname}, {age}, {gender_id})
    """

    UPDATE_PROFILE = """
        UPDATE user_info
        SET {user_name}, {surname}, {age}, {gender_id}
        WHERE id = {id}
    """
