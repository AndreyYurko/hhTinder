class Queries():
    GET_USER_IDS = "select id from users"
    GET_USER_BY_ID = "select * from users where id = {id}"

    GET_VACANCY_IDS = "select id from vacancy"
    GET_VACANCY_BY_ID = "select * from vacancy where id = {id}"

    GET_LIKED_VACANCIES_BY_USERID = "select vacancy_id from user_vac_match where user_id = {id}"

    GET_LIKED_WORKERS_BY_EMPLOYEEID = "select worker_id from employer_worker_match where employee_id = {id}"

    GET_CV_IDS = "select  cv.id from cv join user_info ui on cv.cr_user = ui.cr_user join images i on ui.img_id = i.id join voc_gender vg on ui.gender_id = vg.id join voc_job_category vjc on cv_category = vjc.id"
    GET_CV_BY_ID = "select  cv.id as vac_id, cv_name, cv_text, salary, experience_content, education_content,ui.name, surname, age, i.id as image_id, extension, vg.name as gender, vjc.name as category from cv join user_info ui on cv.cr_user = ui.cr_user join images i on ui.img_id = i.id join voc_gender vg on ui.gender_id = vg.id join voc_job_category vjc on cv_category = vjc.id where cv.id = {id}"
