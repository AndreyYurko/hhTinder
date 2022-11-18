class User():
    def __init__(self, user_data):
        self.id = user_data[0]
        self.email = user_data[1]
        self.name = user_data[2]
        self.passwd = user_data[3]
        self.un_key = user_data[4]
        self.is_lock = user_data[5]
        self.role_id = user_data[6]
        self.cr_user = user_data[7]
        self.cr_date = user_data[8]
        self.upd_date = user_data[9]

    def to_json(self):
        # потом можно будет больше полей дописать, пока не знаю как это будет смотреться на стороны мобилки
        return {self.id, self.email, self.name, self.passwd}


class Vacancy():
    def __init__(self, vacancy_data = ""):
        if vacancy_data == "":
            vacancy_data = []
            for i in range(15):
                vacancy_data.insert(0,"")
            pass

        self.id = vacancy_data[0]
        self.cr_user = vacancy_data[1]
        self.vac_name = vacancy_data[2]
        self.vac_text = vacancy_data[3]
        self.cr_date = vacancy_data[4]
        self.vac_category = vacancy_data[5]
        self.img_id = vacancy_data[6]

    def to_json(self):
        return {"id": self.id, "name": self.vac_name, "body": self.vac_text, "image_id": self.img_id}


class CV():
    def __init__(self, cv_data = ""):
        if cv_data == "":
            cv_data = []
            for i in range(15):
                cv_data.insert(0,"")
            pass

        self.id = cv_data[0]
        self.cv_name = cv_data[1]
        self.cv_text = cv_data[2]
        self.salary = cv_data[3]
        self.experience_content = cv_data[4]
        self.education_content = cv_data[5]
        self.name = cv_data[6]
        self.surname = cv_data[7]
        self.age = cv_data[8]
        self.img_id = cv_data[9]
        self.img_extension = cv_data[10]
        self.gender = cv_data[11]
        self.category = cv_data[12]
        self.image_link = "http://217.25.88.166/web_project/files/images/0/" + str(self.img_id) + "." + self.img_extension



    def to_json(self):
        return {"id": self.id, "name": self.cv_name, "body": self.cv_text, "image_id": self.img_id,
                "image_extension": self.img_extension, "salary": self.salary, "experience": self.experience_content,
                "education": self.education_content, "user_name": self.name, "user_surname": self.surname,
                "user_age": self.age, "gender": self.gender, "category": self.category, "image_link": self.image_link}
