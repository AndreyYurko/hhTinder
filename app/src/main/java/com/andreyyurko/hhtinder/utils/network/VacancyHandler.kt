package com.andreyyurko.hhtinder.utils.network

import android.util.Log
import androidx.lifecycle.ViewModel
import com.andreyyurko.hhtinder.structures.Vacancy
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject

class VacancyHandler @Inject constructor() : ViewModel() {

    companion object {
        const val LOG_TAG = "VacancyHandler"
    }

    private val host = "http://217.25.88.166:8000/vacancy/1"

    private val client = OkHttpClient()

    suspend fun getNextVacancy() : Vacancy{
        try {
            val url = host
            val request = Request.Builder()
                .url(url)
                .get()
                .addHeader("token", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
                .build()
            val response = client.newCall(request).await()
            val responseBody = response.body?.string() ?: ""

            var jsonObj = JSONObject(responseBody)

            var res = Vacancy()

            res.name = jsonObj.getString("name")
            res.content = jsonObj.getString("body")
            res.imageId = jsonObj.getInt("image_id")

            Log.d(VacancyHandler.LOG_TAG, responseBody)

            return res
        } catch (e: Exception) {
            Log.d(VacancyHandler.LOG_TAG, e.toString())
        }

        return Vacancy()
    }
}