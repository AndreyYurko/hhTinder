package com.andreyyurko.hhtinder.utils.handlers

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyyurko.hhtinder.structures.Vacancy
import com.andreyyurko.hhtinder.utils.network.VacancyHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMatchesHandler @Inject constructor() : ViewModel() {

    private val _loadUsersActionState = MutableStateFlow<DataState>(DataState.Loading)
    val loadUsersActionState: Flow<DataState> get() = _loadUsersActionState.asStateFlow()

    private val host = "genius.p.rapidapi.com"

    private val client = OkHttpClient()

    fun getCards() {
        viewModelScope.launch {

            try {
                val url = host
                val request = Request.Builder()
                    .url(url)
                    .get()
                    .build()
                val response = client.newCall(request).await()
                val responseBody = response.body?.string() ?: ""

                var jsonObj = JSONObject(responseBody)

                var res = Vacancy()

                res.name = jsonObj.getString("name")
                res.content = jsonObj.getString("body")
                res.imageId = jsonObj.getInt("image_id")

                Log.d(VacancyHandler.LOG_TAG, responseBody)

                _loadUsersActionState.emit(DataState.Data(res))
            } catch (e: Exception) {
                _loadUsersActionState.emit(DataState.Error)
                Log.d(VacancyHandler.LOG_TAG, e.toString())
            }
        }
    }

    sealed class DataState {
        object Loading : DataState()
        data class Data(val data: Vacancy) : DataState()
        object Error : DataState()
    }
}

data class Card(
    val drawable: Drawable,
)