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
    def __init__(self, vacancy_data):
        self.id = vacancy_data[0]
        self.cr_user = vacancy_data[1]
        self.vac_name = vacancy_data[2]
        self.vac_text = vacancy_data[3]
        self.cr_date = vacancy_data[4]
        self.vac_category = vacancy_data[5]
        self.img_id = vacancy_data[6]

    def to_json(self):
        return {"id": self.id, "name": self.vac_name, "body": self.vac_text, "image_id": self.img_id}
