package com.andreyyurko.hhtinder.utils.network

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.ViewModel
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHandler @Inject constructor() : ViewModel() {

    companion object {
        const val LOG_TAG = "AuthHandler"
    }

    private val host = "http:217.25.88.166:8000/login/"

    private val client = OkHttpClient()

    private val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    private val _authStateFlow = MutableStateFlow<AuthState>(AuthState.NotLogged)

    fun authStateFlow(): Flow<AuthState> {
        return _authStateFlow.asStateFlow()
    }

    suspend fun send(log: String, pass: String, token: String): Boolean {

        var type = "password"
        var key = pass

        if (token != "") {
            type = "token"
            key = token
        }

        val body = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), "")

        val url = host + type + "/" + log + "/" + key
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).await()
        val responseBody = response.body?.string() ?: ""

        var res = false

        try {
            var jsonObj = JSONObject(responseBody)

            if (type.equals("password")) {
                var token = jsonObj.getString("token")

                SharedPreferencesSingleton.instance.getSharedPreferences()!!.edit()
                    .putString("token", token).commit()

                SharedPreferencesSingleton.instance.getSharedPreferences()!!.edit()
                    .putString("login", log).commit()


                res = !token.equals("")
            } else {
                res = jsonObj.getBoolean("login")
            }

            Log.d(LOG_TAG, responseBody)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return res
    }

    suspend fun logIn(log: String?, pass: String?, token: String?) {
        if (log != null && pass != null && token != null) {
            val res = send(log, pass, token)
            if (res) {
                _authStateFlow.emit(AuthState.Logged)
            }
        }
    }

    sealed class AuthState {
        object Logged : AuthState()
        object NotLogged : AuthState()
    }
}