package com.yu.pl.app.challeng.notemark.functions.presentation.registration.model

import com.yu.pl.app.challeng.notemark.core.domain.EmailValidation
import com.yu.pl.app.challeng.notemark.core.domain.PasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.RepeatPasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.UserNameValidation

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




