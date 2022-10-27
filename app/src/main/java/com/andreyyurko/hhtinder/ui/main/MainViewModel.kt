package com.andreyyurko.hhtinder.ui.main

import androidx.lifecycle.ViewModel
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authHandler: AuthHandler
) : ViewModel()  {

}