package com.andreyyurko.hhtinder.ui.entrance

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntranceViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel()  {

    fun auth(log: String?, pass: String?, token: String?) {
        Log.d("AuthHandler", "$log   $token")
        viewModelScope.launch {
            authHandler.logIn(log, pass, token)
        }
    }

}