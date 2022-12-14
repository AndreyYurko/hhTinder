package com.andreyyurko.hhtinder.utils.handlers

import android.util.Log
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileHandler @Inject constructor() {

    data class Profile(
        var name: String = "",
        var surname: String = "",
        var age: String = "",
        var genderId: Int = 1,
        var imgId: Int = 0,
        var crUser: Int = 0,
    )

    var profileInfo = Profile()

    private val host = "http:217.25.88.166:8000/"
    private val client = OkHttpClient()
    private val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    suspend fun addProfile() {
        val url = host + "add_profile/"
        val bodyJson = Gson().toJson(profileInfo)
        val body = bodyJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("token", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855")
            .build()

        val response = client.newCall(request).await()
        Log.d(AuthHandler.LOG_TAG, response.toString())
        val responseBody = response.body?.string() ?: ""

        Log.d("Profile", responseBody)
    }

}