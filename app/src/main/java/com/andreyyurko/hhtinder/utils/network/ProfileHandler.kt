package com.andreyyurko.hhtinder.utils.network

import android.util.Log
import com.andreyyurko.hhtinder.structures.Profile
import com.andreyyurko.hhtinder.structures.Vacancy
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await

class ProfileHandler {

    companion object {
        const val LOG_TAG = "ProfileHandler"
    }

    private val host = "http://217.25.88.166:8000/profile/"

    private val client = OkHttpClient()

    suspend fun getUserInfo(login: String): Profile {
        try {
            val url = host + login
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader(
                    "token",
                    "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
                )
                .build()

            val response = client.newCall(request).await()
            val responseBody = response.body?.string() ?: ""

            var jsonObj = JSONObject(responseBody)

            var res = Profile()

            res.id = jsonObj.getInt("id");
            res.email = jsonObj.getString("email")
            res.login = jsonObj.getString("login")
            res.password = jsonObj.getString("password")
            res.name = jsonObj.getString("name")
            res.surname = jsonObj.getString("surname")
            res.age = jsonObj.getInt("age")
            res.genderID = jsonObj.getInt("gender_id")
            res.roleID = jsonObj.getInt("role_id")
            res.imgURL = jsonObj.getString("img_url")

            Log.d(VacancyHandler.LOG_TAG, responseBody)

            return res
        } catch (e: Exception) {
            Log.d(VacancyHandler.LOG_TAG, e.toString())
        }

        return Profile()
    }
}