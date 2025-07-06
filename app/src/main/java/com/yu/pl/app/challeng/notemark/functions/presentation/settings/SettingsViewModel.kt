package com.yu.pl.app.challeng.notemark.functions.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.functions.domain.auth.AuthRepository
import com.yu.pl.app.challeng.notemark.functions.domain.note.NoteMarkRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val noteMarkRepository: NoteMarkRepository,
    private val authTokenRepository: AuthTokenRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState())
    val state = _state.onStart {
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SettingsState()
    )

    private val _event = Channel<SettingsEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: SettingsAction){
        when(action){
            SettingsAction.OnLogout -> logout()
            SettingsAction.OnBackNavigation -> Unit
        }
    }

    private fun logout(){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            val authToken = authTokenRepository.getToken()?:run {
                throw IllegalStateException()
            }
            if(authRepository.logout(authToken.refreshToken).isFailure) {
                _event.send(SettingsEvent.Error)
                return@launch
            }
            noteMarkRepository.deleteLocalNotes()
            authTokenRepository.deleteToken()
            _event.send(SettingsEvent.NavigateLogin)
        }.invokeOnCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

}