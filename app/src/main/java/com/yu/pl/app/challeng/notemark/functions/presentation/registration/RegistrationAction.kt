package com.yu.pl.app.challeng.notemark.functions.presentation.registration

sealed interface RegistrationAction {
    data class OnUserNameInput(val userName:String): RegistrationAction
    data class OnEmailInput(val email:String): RegistrationAction
    data class OnPasswordInput(val password:String): RegistrationAction
    data class OnRepeatPasswordInput(val repeatPassword:String): RegistrationAction
    data object OnCreateAccount: RegistrationAction
    data object OnAlreadyHaveAccount: RegistrationAction
    data object OnTogglePasswordShow: RegistrationAction
    data object OnToggleRepeatPasswordShow:RegistrationAction

}