package com.andreyyurko.hhtinder.ui

import androidx.lifecycle.ViewModel
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel() {


    fun authStateFlow() : Flow<AuthHandler.AuthState> {
        return authHandler.authStateFlow()
    }


}