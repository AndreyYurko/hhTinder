package com.andreyyurko.hhtinder.utils.network

import android.graphics.drawable.Drawable
import android.util.Log
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.structures.Card
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import java.io.InputStream
import java.net.URL

class ArchiveHandler {

    companion object {
        const val LOG_TAG = "ArchiveHandler"
    }

    private val host = "http://217.25.88.166:8000/" + getTopic() + "/" + getName()

    private val client = OkHttpClient()

    suspend fun getArchiveList(): List<Card> {
        var res: ArrayList<Card> = ArrayList()

        val url = host
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("token", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
            .build()

        val response = client.newCall(request).await()
        val responseBody = response.body?.string() ?: ""


        try {
            var jsonObj = JSONObject(responseBody)
            jsonObj.keys().forEachRemaining {
                val key = it
                val archive = jsonObj.getJSONObject(key)
                val name = archive.getString("name")
                var content = archive.getString("content")
                content = content.substring(0, Math.min(40, content.length))
                var parts = content.split(" ")
                content = ""
                var flag = false
                parts.forEach {
                    if (content.length > 15 && !flag) {
                        flag = true
                        content += it + "\n"
                    } else {
                        content += it + " "
                    }
                }
                var imgUrl = ""
                try {
                    imgUrl =
                        "http://217.25.88.166/web_project/files/images/0/" + archive.getInt("image_id") + "." + archive.getString(
                            "img_ext"
                        )
                } catch (e: Exception) {

                }

                var arch = Card(key.toInt(), name, content)

                try {
                    val URLcontent: InputStream =
                        URL(imgUrl).getContent() as InputStream
                    arch.image = Drawable.createFromStream(URLcontent, "your source link");
                } catch (e: Exception) {
                    Log.d(LOG_TAG, "Image URL: " + imgUrl)
                    Log.d(LOG_TAG, "Load image failed: " + e.toString())
                }

                res.add(arch)
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