package com.yu.pl.app.challeng.notemark.features.presentation.landing

sealed interface LandingAction {
    data object OnGetStartClick: LandingAction
    data object OnLoginClick: LandingAction
}