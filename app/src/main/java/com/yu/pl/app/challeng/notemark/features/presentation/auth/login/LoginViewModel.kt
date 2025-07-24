package com.yu.pl.app.challeng.notemark.features.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.features.domain.auth.AccountValidator
import com.yu.pl.app.challeng.notemark.features.domain.auth.EmailValidation
import com.yu.pl.app.challeng.notemark.core.ui.util.UiText
import com.yu.pl.app.challeng.notemark.features.domain.auth.LoginResult
import com.yu.pl.app.challeng.notemark.features.domain.auth.LoginUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validator: AccountValidator,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.onStart {
        observePassword()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        LoginState()
    )

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnChangeEmailValue -> changeEmailValue(action.email)
            is LoginAction.OnChangePasswordValue -> changePasswordValue(action.password)
            is LoginAction.OnTogglePasswordVisible -> togglePasswordVisible()
            LoginAction.OnLogin -> login()
            LoginAction.OnNotHaveAccount -> Unit
        }
    }

    private fun changeEmailValue(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun changePasswordValue(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun togglePasswordVisible() {
        _state.update {
            it.copy(
                isVisiblePassword = !it.isVisiblePassword
            )
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            val result = loginUseCase(
                email = _state.value.email,
                password = _state.value.password
            ).getOrNull() ?: run {
                _event.send(LoginEvent.ShowToastMessage(UiText.ResourceString(R.string.login_fail_unknown)))
                return@launch
            }

            when (result) {
                LoginResult.DataSyncError -> {
                    _event.send(LoginEvent.ShowToastMessage(UiText.ResourceString(R.string.login_fail_data_sync)))
                }

                LoginResult.ServeSideError -> {
                    _event.send(LoginEvent.ShowToastMessage(UiText.ResourceString(R.string.login_fail_server_side)))
                }

                LoginResult.Success -> {
                    _event.send(LoginEvent.SuccessLogin)
                }

                LoginResult.UnAuthorized -> {
                    _event.send(LoginEvent.ShowToastMessage(UiText.ResourceString(R.string.login_fail_unauthroized)))

                }

                LoginResult.UnknownError -> {
                    _event.send(LoginEvent.ShowToastMessage(UiText.ResourceString(R.string.login_fail_unknown)))
                }
            }
        }.invokeOnCompletion {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun observePassword() {
        combine(
            _state.map { it.email },
            _state.map { it.password }
        ) { email, password ->
            _state.update {
                it.copy(
                    validLogin =
                        validator.validateEmail(email) == EmailValidation.Valid &&
                                password.isNotBlank()
                )
            }
        }.launchIn(viewModelScope)
    }
}
