package com.yu.pl.app.challeng.notemark.functions.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.domain.AccountValidator
import com.yu.pl.app.challeng.notemark.core.domain.EmailValidation
import com.yu.pl.app.challeng.notemark.core.presentation.UiText
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginRepository
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginRequest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
    private val loginRepository: LoginRepository,
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

            val result = loginRepository.login(
                requestData = LoginRequest(
                    email = _state.value.email,
                    password = _state.value.password
                )
            )
            _state.update { it.copy(isLoading = false) }

            if (result.isSuccess) {
                _event.send(LoginEvent.SuccessLogin)
            }

            if (result.isFailure) {
                _event.send(LoginEvent.ShowSnackBar(UiText.ResourceString(R.string.login_invalid)))
            }
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
