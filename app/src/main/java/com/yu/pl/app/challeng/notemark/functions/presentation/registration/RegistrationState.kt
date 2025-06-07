package com.yu.pl.app.challeng.notemark.functions.presentation.registration

import com.yu.pl.app.challeng.notemark.functions.presentation.registration.model.ValidationModel

data class RegistrationState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val validate: ValidationModel = ValidationModel(),
    val isAllValid: Boolean = false,
    val isVisiblePassword: Boolean = false,
    val isVisibleRepeatPassword: Boolean = false,
    val isLoading: Boolean = false
)

