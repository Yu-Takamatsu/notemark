package com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.model

import com.yu.pl.app.challeng.notemark.features.domain.auth.EmailValidation
import com.yu.pl.app.challeng.notemark.features.domain.auth.PasswordValidation
import com.yu.pl.app.challeng.notemark.features.domain.auth.RepeatPasswordValidation
import com.yu.pl.app.challeng.notemark.features.domain.auth.UserNameValidation

data class ValidationModel(
    val userName: UserNameValidation = UserNameValidation.Empty,
    val email: EmailValidation = EmailValidation.Empty,
    val password: PasswordValidation = PasswordValidation.Empty,
    val repeatPassword: RepeatPasswordValidation = RepeatPasswordValidation.Empty,
) {
    fun isAllParamValidate() = userName == UserNameValidation.Valid &&
            email == EmailValidation.Valid && password == PasswordValidation.Valid &&
            repeatPassword == RepeatPasswordValidation.Valid
}




