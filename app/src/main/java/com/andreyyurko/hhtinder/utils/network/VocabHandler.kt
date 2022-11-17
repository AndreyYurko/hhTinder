package com.andreyyurko.hhtinder.utils.network

import android.util.Log
import com.andreyyurko.hhtinder.structures.Vocab
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await

class VocabHandler {

    companion object {
        const val LOG_TAG = "VacancyHandler"
        val instance = VocabHandler()
    }

    private val host = "http://217.25.88.166:8000/voc/"

    private val client = OkHttpClient()

    suspend fun getVocab(name: String): List<Vocab> {
        try {
            val url = host + name
            val request = Request.Builder()
                .url(url)
                .get()
                .build()
            val response = client.newCall(request).await()
            val responseBody = response.body?.string() ?: ""

            var jsonObj = JSONObject(responseBody)

            var res = ArrayList<Vocab>()

            jsonObj.keys().forEach {
                val id = it
                val name = jsonObj.getString(id)
                var voc = Vocab(id.toInt(), name)
                res.add(voc)
            }

            Log.d(VacancyHandler.LOG_TAG, responseBody)

            return res
        } catch (e: Exception) {
            Log.d(VacancyHandler.LOG_TAG, e.toString())
        }

        return emptyList()
    }
}