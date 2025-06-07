package com.yu.pl.app.challeng.notemark.functions.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _isShowSplash = MutableStateFlow(true)
    val isShowSplash = _isShowSplash.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isShowSplash.update {
                false
            }
        }
    }

}