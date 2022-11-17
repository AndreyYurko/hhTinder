package com.andreyyurko.hhtinder.utils.network

import android.util.Log
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.structures.Archive
import com.andreyyurko.hhtinder.structures.Vacancy
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await

class ArchiveHandler {

    companion object {
        const val LOG_TAG = "ArchiveHandler"
    }

    private val host = "http://217.25.88.166:8000/" + getTopic() + "/" + getName()

    private val client = OkHttpClient()

    suspend fun getArchiveList(): List<Archive> {
        var res: ArrayList<Archive> = ArrayList()

        val url = host
        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val response = client.newCall(request).await()
        val responseBody = response.body?.string() ?: ""

        var jsonObj = JSONObject(responseBody)
        try {
            jsonObj.keys().forEachRemaining {
                val key = it
                val archive = jsonObj.getJSONObject(key)
                val name = archive.getString("name")
                val content = archive.getString("content")
                res.add(Archive(key.toInt(), name, content))
            }
            Log.d(LOG_TAG, responseBody)
            return res
        } catch (e: Exception) {
            Log.d(LOG_TAG, e.toString())
        }

        return res
    }

    fun getTopic(): String {
        val roleId =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!
                .getInt("role_id", 2);
        if (roleId == 2) {
            return "vacancy_preview_info"
        } else {
            return "cv_preview_info"
        }
    }

    fun getName(): String? {
        return SharedPreferencesSingleton.instance.getSharedPreferences()!!
            .getString("login", "")
    }
}