package com.andreyyurko.hhtinder.ui.main

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.structures.CV
import com.andreyyurko.hhtinder.utils.network.CVHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cvHandler : CVHandler) : ViewModel()  {

    companion object {
        const val LOG_TAG = "MainViewModel"
    }

    private var _cvList = mutableListOf<CV>()
    private var _index = 0
    private val _getCVState = MutableStateFlow<GetCVState>(GetCVState.Loading)
    fun getCVState(): StateFlow<GetCVState> {
        return _getCVState.asStateFlow()
    }

    init {
        Log.d(LOG_TAG, "ViewModel inited")
        loadVacancy()
    }

    fun loadVacancy() {
        Log.d(LOG_TAG, "loading vacancy")
        viewModelScope.launch {
            _cvList = mutableListOf()
            _index = 0
            _getCVState.emit(GetCVState.Loading)
            delay(3000)
            for (i in 0..4) {
                val cv = cvHandler.getNextCV()
                _cvList.add(cv)
                //Log.d(LOG_TAG, cv.name)
            }
            //Log.d(LOG_TAG, _cvList.size.toString())
            _getCVState.emit(GetCVState.Ok)
        }
    }
    fun getCVListSize(): Int {
        return _cvList.size
    }

    fun getCV(index: Int): CV {
        return _cvList[index]
    }

    fun getIndex(): Int {
        return _index
    }

    fun increaseIndex() {
        _index += 1
    }

    sealed class GetCVState {
        object Ok : GetCVState()
        object Error : GetCVState()
        object Loading: GetCVState()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(LOG_TAG, "viewModel is cleared")
    }
}