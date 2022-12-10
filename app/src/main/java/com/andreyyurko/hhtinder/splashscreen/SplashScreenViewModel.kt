package com.andreyyurko.hhtinder.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel() {
    fun auth(log: String?, pass: String?, token: String?) {
        viewModelScope.launch {
            authHandler.logIn(log, pass, token)
        }
    }
}