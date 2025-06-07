package com.yu.pl.app.challeng.notemark.core.domain

interface AccountValidator {
    fun validateUserName(userName:String): UserNameValidation
    fun validateEmail(email:String): EmailValidation
    fun validatePassword(password:String): PasswordValidation
    fun validateRepeatPassword(repeatPassword:String, password: String): RepeatPasswordValidation
}

enum class UserNameValidation{
    Empty,
    Short,
    Long,
    Invalid,
    Valid
}

enum class EmailValidation{
    Empty,
    Invalid,
    Valid
}

enum class PasswordValidation{
    Empty,
    Invalid,
    Valid
}

enum class RepeatPasswordValidation{
    Empty,
    Invalid,
    Valid
}
