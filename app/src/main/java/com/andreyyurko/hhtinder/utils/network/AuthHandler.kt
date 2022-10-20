package com.andreyyurko.hhtinder.utils.network

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHandler @Inject constructor() : ViewModel() {

    companion object {
        const val LOG_TAG = "AuthHandler"
    }

    private val host = "http://192.168.1.3:80/"

    private val client = OkHttpClient()

    private val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()

    private val _authStateFlow = MutableStateFlow<AuthState>(AuthState.NotLogged)

    fun authStateFlow() : Flow<AuthState> {
        return _authStateFlow.asStateFlow()
    }

    suspend fun send() {
        val url = host
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        val response = client.newCall(request).await()
        val responseBody = response.body?.string() ?: ""
        Log.d(LOG_TAG, responseBody)
    }

    suspend fun logIn() {
        _authStateFlow.emit(AuthState.Logged)
    }

    sealed class AuthState {
        object Logged : AuthState()
        object NotLogged : AuthState()
    }
}