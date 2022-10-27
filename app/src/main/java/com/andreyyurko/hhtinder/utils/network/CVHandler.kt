package com.andreyyurko.hhtinder.utils.network

import android.graphics.drawable.Drawable
import android.util.Log
import com.andreyyurko.hhtinder.structures.CV
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import java.io.InputStream
import java.net.URL

class CVHandler {

    companion object {
        const val LOG_TAG = "VacancyHandler"
    }

    private val host = "http://217.25.88.166:8000/cv/1"

    private val client = OkHttpClient()

    suspend fun getNextCV(): CV {
        try {
            val url = host
            val request = Request.Builder().url(url).get().build()
            val response = client.newCall(request).await()
            val responseBody = response.body?.string() ?: ""

            var jsonObj = JSONObject(responseBody)

            var res = CV()

            res.name = jsonObj.getString("name")
            res.content = jsonObj.getString("body")
            res.salary = jsonObj.getInt("salary")
            res.experience = jsonObj.getString("experience")
            res.education = jsonObj.getString("education")
            res.category = jsonObj.getString("category")

            res.imageLink = jsonObj.getString("image_link")

            res.userName = jsonObj.getString("user_name")
            res.userSurname = jsonObj.getString("user_surname")
            res.userAge = jsonObj.getInt("user_age")
            res.userGender = jsonObj.getString("gender")

            try {
                val URLcontent: InputStream =
                    URL(jsonObj.getString("image_link")).getContent() as InputStream
                res.image = Drawable.createFromStream(URLcontent, "your source link");
            } catch (e: Exception) {
                Log.d(VacancyHandler.LOG_TAG, "Load image failed: " + e.toString())
            }

            Log.d(VacancyHandler.LOG_TAG, responseBody)

            return res
        } catch (e: Exception) {
            Log.d(VacancyHandler.LOG_TAG, e.toString())
        }

        return CV()
    }
}