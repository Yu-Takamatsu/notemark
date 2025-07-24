package com.yu.pl.app.challeng.notemark.features.presentation.auth.registration

import com.yu.pl.app.challeng.notemark.core.ui.util.UiText

sealed interface RegistrationEvent {
    data class ShowToastMessage(val message: UiText): RegistrationEvent
    data object SuccessRegistration: RegistrationEvent
}