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