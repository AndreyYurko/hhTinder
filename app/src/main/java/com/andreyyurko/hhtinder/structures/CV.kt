package com.andreyyurko.hhtinder.structures

import android.graphics.drawable.Drawable
import org.json.JSONObject

class CV {
    var id = 0

    var name: String = "Name"
    var content: String = "Body"
    var salary = 0
    var experience: String = ""
    var education: String = ""
    var category: String = ""

    var imageLink: String = ""
    var imgID = 0
    var crUser = 0;

    var userName: String = ""
    var userSurname: String = ""
    var userAge = 0
    var userGender: String = ""
    var crDate = ""

    var image: Drawable? = null

    fun toJSON(): JSONObject {
        var res = JSONObject()
        res.put("id", id);
        res.put("cr_user", crUser)
        res.put("cv_name", name)
        res.put("cv_text", content)
        res.put("cr_date", crDate)
        res.put("img_id", imgID)
        res.put("cv_category", category)
        res.put("salary", salary)
        res.put("experience_content", experience)
        res.put("education_content", education)
        return res;
    }
}