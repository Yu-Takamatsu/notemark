package com.yu.pl.app.challeng.notemark.functions.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isVisiblePassword: Boolean = false,
    val validLogin: Boolean = false,
    val isLoading: Boolean = false
) {
}