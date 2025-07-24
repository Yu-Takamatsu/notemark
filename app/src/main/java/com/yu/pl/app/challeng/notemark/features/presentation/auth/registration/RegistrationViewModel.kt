package com.yu.pl.app.challeng.notemark.features.presentation.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.features.domain.auth.AccountValidator
import com.yu.pl.app.challeng.notemark.core.ui.util.UiText
import com.yu.pl.app.challeng.notemark.features.domain.auth.RegisterAccountUseCase
import com.yu.pl.app.challeng.notemark.features.domain.auth.RegisterResult
import com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.model.ValidationModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val validator: AccountValidator,
    private val registerAccountUseCase: RegisterAccountUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RegistrationState())
    val state = _state.onStart {
        observeInputValue()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RegistrationState()
    )

    private val _event = Channel<RegistrationEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: RegistrationAction) {
        when (action) {
            RegistrationAction.OnAlreadyHaveAccount -> Unit
            RegistrationAction.OnCreateAccount -> createAccount()
            is RegistrationAction.OnEmailInput -> inputEmail(action.email)
            is RegistrationAction.OnPasswordInput -> inputPassword(action.password)
            is RegistrationAction.OnRepeatPasswordInput -> inputRepeatPassword(action.repeatPassword)
            is RegistrationAction.OnUserNameInput -> inputUserName(action.userName)
            RegistrationAction.OnTogglePasswordShow -> togglePasswordShow()
            RegistrationAction.OnToggleRepeatPasswordShow -> toggleRepeatPasswordShow()
        }
    }

    private fun createAccount() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            val result = registerAccountUseCase(
                email = _state.value.email,
                password = _state.value.password,
                username = _state.value.userName
            ).getOrNull() ?: run {
                _event.send(RegistrationEvent.ShowToastMessage(UiText.ResourceString(R.string.registration_fail_unknown)))
                return@launch
            }

            when (result) {
                RegisterResult.InvalidError -> {
                    _event.send(RegistrationEvent.ShowToastMessage(UiText.ResourceString(R.string.registration_fail_invalid)))
                }
                RegisterResult.ServiceSideError -> {
                    _event.send(RegistrationEvent.ShowToastMessage(UiText.ResourceString(R.string.registration_fail_server)))
                }

                RegisterResult.Success -> {
                    _event.send(RegistrationEvent.ShowToastMessage(UiText.ResourceString(R.string.registration_success)))
                }

                RegisterResult.Conflict,
                RegisterResult.UnexpectedError,
                    -> {
                    _event.send(RegistrationEvent.ShowToastMessage(UiText.ResourceString(R.string.registration_fail_unknown)))
                }
            }
        }.invokeOnCompletion {
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    private fun togglePasswordShow() {
        _state.update {
            it.copy(isVisiblePassword = !it.isVisiblePassword)
        }

    }

    private fun toggleRepeatPasswordShow() {
        _state.update {
            it.copy(isVisibleRepeatPassword = !it.isVisibleRepeatPassword)
        }

    }

    private fun inputEmail(email: String) {
        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun inputUserName(userName: String) {
        _state.update {
            it.copy(
                userName = userName
            )
        }
    }

    private fun inputPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun inputRepeatPassword(password: String) {
        _state.update {
            it.copy(
                repeatPassword = password
            )
        }
    }


    private fun observeInputValue() {
        combine(
            _state.map { it.userName },
            _state.map { it.email },
            _state.map { it.password },
            _state.map { it.repeatPassword }
        ) { userName, email, password, repeatPassword ->

            ValidationModel(
                userName = validator.validateUserName(userName),
                email = validator.validateEmail(email),
                password = validator.validatePassword(password),
                repeatPassword = validator.validateRepeatPassword(repeatPassword, password)
            )
        }.onEach { validate ->
            _state.update {
                it.copy(
                    validate = validate,
                    isAllValid = validate.isAllParamValidate()
                )
            }
        }.launchIn(viewModelScope)
    }
}