package com.andreyyurko.hhtinder.structures

import android.graphics.drawable.Drawable
import org.json.JSONObject

class Vacancy {
    var id = 0

    var name: String = "Name"
    var content: String = "Body"
    var salary = 0
    var experience: String = ""
    var education: String = ""
    var category: String = ""

    var imageLink: String = ""

    var userName: String = ""
    var userSurname: String = ""
    var userAge = 0
    var userGender: String = ""

    var crUser = 0;
    var crDate = ""

    var imageId: Int = 0

    fun toJSON(): JSONObject {
        var res = JSONObject()
        res.put("id", id)
        res.put("cr_user", crUser)
        res.put("vac_name", name)
        res.put("vac_text", content)
        res.put("cr_date", crDate)
        res.put("vac_category", Integer.parseInt(category))
        res.put("img_id", imageId)

        return res
    }
}