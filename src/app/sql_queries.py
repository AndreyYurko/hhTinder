class Queries():
    GET_USER_IDS = "select id from users"
    GET_USER_BY_ID = "select * from users where id = {id}"

    GET_VACANCY_IDS = "select id from vacancy"
    GET_VACANCY_BY_ID = "select * from vacancy where id = {id}"

    GET_LIKED_VACANCIES_ID_BY_USERID = "select vacancy_id from user_vac_match where user_id = {id}"

    GET_LIKED_WORKERS_ID_BY_EMPLOYEEID = "select worker_id from employer_worker_match where employee_id = {id}"

    GET_CV_IDS = "select  cv.id from cv join user_info ui on cv.cr_user = ui.cr_user join images i on ui.img_id = i.id join voc_gender vg on ui.gender_id = vg.id join voc_job_category vjc on cv_category = vjc.id"
    GET_CV_BY_ID = "select  cv.id as vac_id, cv_name, cv_text, salary, experience_content, education_content,ui.name, surname, age, i.id as image_id, extension, vg.name as gender, vjc.name as category from cv join user_info ui on cv.cr_user = ui.cr_user join images i on ui.img_id = i.id join voc_gender vg on ui.gender_id = vg.id join voc_job_category vjc on cv_category = vjc.id where cv.id = {id}"

    INSERT_LIKE_FROM_VACANCY = "insert into employer_worker_match values ({vacancy_id}, {user_id})"
    INSERT_LIKE_FROM_WORKER = "insert into user_vac_match values ({user_id}, {vacancy_id})"

    GET_JOB_CATEGORIES = "select * from voc_job_category"

    LOGIN_BY_LOG_AND_PASS = "select * from users where email like '{email}' and passwd like '{passwd}' limit 1"
    LOGIN_BY_LOG_AND_TOKEN = "select * from users where email like '{email}' and un_key like '{un_key}' limit 1"

    PUSH_TOKEN_BY_LOG = "UPDATE users SET un_key = '{un_key}' WHERE (email LIKE '{email}' AND passwd LIKE '{passwd}')"


    GET_USER_ROLE_BY_EMAIL = "SELECT role_id, roles.name from users join users_role roles on users.role_id = roles.id where users.email = '{email_str}'"

    GET_VACANCY_PREVIEW_INFO = "SELECT vac_name, vac_text, vac_category, img_id, images.name, images.extension from vacancy join users on vacancy.cr_user = users.id join images on vacancy.img_id = images.id where users.email = '{email_str}'"
    GET_CV_PREVIEW_INFO = "SELECT cv_name, salary, cv_text, cv_category, img_id, images.name, images.extension from cv join users on cv.cr_user = users.id join images on cv.img_id = images.id where users.email = '{email_str}'"

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