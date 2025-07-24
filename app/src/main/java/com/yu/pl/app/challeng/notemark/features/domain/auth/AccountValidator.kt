package com.yu.pl.app.challeng.notemark.features.domain.auth

class AccountValidator {
    fun validateUserName(userName:String): UserNameValidation{
        if (userName.isBlank()) return UserNameValidation.Empty
        if (userName.length < 3) return UserNameValidation.Short
        if (userName.length > 20) return UserNameValidation.Long
        val regex = "^[a-zA-Z0-9.-]{3,20}$".toRegex()
        return if (userName.matches(regex)) UserNameValidation.Valid else UserNameValidation.Invalid
    }
    fun validateEmail(email:String): EmailValidation{
        if(email.isBlank()) return EmailValidation.Empty
        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex()
        return if (email.matches(emailRegex)) EmailValidation.Valid else EmailValidation.Invalid
    }
    fun validatePassword(password:String): PasswordValidation{
        if(password.isBlank()) return PasswordValidation.Empty
        val regex = "^(?=.*[0-9!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$".toRegex()
        return if(password.matches(regex)) PasswordValidation.Valid else PasswordValidation.Invalid
    }
    fun validateRepeatPassword(repeatPassword:String, password: String): RepeatPasswordValidation{
        if(repeatPassword.isBlank()) return RepeatPasswordValidation.Empty
        return if(repeatPassword == password) RepeatPasswordValidation.Valid else RepeatPasswordValidation.Invalid
    }
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
