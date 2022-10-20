package com.andreyyurko.hhtinder.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel()  {

    fun auth() {
        viewModelScope.launch {
            authHandler.logIn()
        }
    }
}