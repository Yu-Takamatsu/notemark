package com.yu.pl.app.challeng.notemark.features.presentation.landing

import androidx.compose.runtime.Composable
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType

@Composable
fun LandingRoot(
    onNavigateToLogin:()->Unit,
    onNavigateToRegistration:()->Unit
) {

    val layoutType = getLayoutType()

    val action:(action: LandingAction)-> Unit = { action ->
        when(action){
            LandingAction.OnGetStartClick -> onNavigateToRegistration()
            LandingAction.OnLoginClick -> onNavigateToLogin()
        }
    }
    LandingScreen(
        action = action,
        layoutType = layoutType
    )
}