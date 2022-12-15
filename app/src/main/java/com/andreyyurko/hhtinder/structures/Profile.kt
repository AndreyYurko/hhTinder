package com.andreyyurko.hhtinder.structures

import org.json.JSONObject

class Profile {
    var id = 0
    var email = ""
    var login = ""
    var password = ""
    var name = ""
    var surname = ""
    var age = 0
    var genderID = 0
    var roleID = 0
    var imgID = 0
    var creationUser = 0
    var imgURL = ""

    fun toJSON(): JSONObject {
        var res = JSONObject()

        res.put("id", id)
        res.put("name", name)
        res.put("surname", surname)
        res.put("age", "" + age)
        res.put("gender_id", genderID)
        res.put("img_id", imgID)
        res.put("cr_user", creationUser)

        return res
    }
}