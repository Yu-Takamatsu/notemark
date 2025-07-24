package com.yu.pl.app.challeng.notemark.features.presentation.auth.login

sealed interface LoginAction {
    data class OnChangeEmailValue(val email:String): LoginAction
    data class OnChangePasswordValue(val password:String): LoginAction
    data object OnTogglePasswordVisible: LoginAction
    data object OnLogin: LoginAction
    data object OnNotHaveAccount: LoginAction
}