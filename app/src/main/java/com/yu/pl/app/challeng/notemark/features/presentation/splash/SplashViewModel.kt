package com.yu.pl.app.challeng.notemark.features.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import com.yu.pl.app.challeng.notemark.navigation.Destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenRepository: PreferenceRepository
): ViewModel() {

    private val _isShowSplash = MutableStateFlow(true)
    val isShowSplash = _isShowSplash.asStateFlow()

    private val _startDestination = MutableStateFlow<Destination?>(null)
    val startDestination = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)

            _startDestination.update {
                if(tokenRepository.getUserName()?.isNotBlank() == true){
                    Destination.NoteList
                }else{
                    Destination.Landing
                }
            }
            _isShowSplash.update {
                false
            }
        }
    }

}