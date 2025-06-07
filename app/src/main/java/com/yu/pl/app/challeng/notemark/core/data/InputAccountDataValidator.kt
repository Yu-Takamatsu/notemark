package com.yu.pl.app.challeng.notemark.core.data

import com.yu.pl.app.challeng.notemark.core.domain.AccountValidator
import com.yu.pl.app.challeng.notemark.core.domain.EmailValidation
import com.yu.pl.app.challeng.notemark.core.domain.PasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.RepeatPasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.UserNameValidation

class InputAccountDataValidator : AccountValidator {
    override fun validateUserName(userName: String): UserNameValidation {
        if (userName.isBlank()) return UserNameValidation.Empty
        if (userName.length < 3) return UserNameValidation.Short
        if (userName.length > 20) return UserNameValidation.Long
        val regex = "^[a-zA-Z0-9.-]{3,20}$".toRegex()
        return if (userName.matches(regex)) UserNameValidation.Valid else UserNameValidation.Invalid
    }

    override fun validateEmail(email: String): EmailValidation {
        if(email.isBlank()) return EmailValidation.Empty
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        return if (email.matches(emailRegex)) EmailValidation.Valid else EmailValidation.Invalid

    }

    override fun validatePassword(password: String): PasswordValidation {
        if(password.isBlank()) return PasswordValidation.Empty
        val regex = "^(?=.*[0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$".toRegex()
        return if(password.matches(regex)) PasswordValidation.Valid else PasswordValidation.Invalid
    }

    override fun validateRepeatPassword(
        repeatPassword: String,
        password: String,
    ): RepeatPasswordValidation {
        if(repeatPassword.isBlank()) return RepeatPasswordValidation.Empty
        return if(repeatPassword == password) RepeatPasswordValidation.Valid else RepeatPasswordValidation.Invalid
    }
}