package com.yu.pl.app.challeng.notemark.functions.presentation.landing

import androidx.compose.runtime.Composable
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.screen.LandscapeLandingScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.screen.PortraitLandingScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.screen.TabletLandingScreen
import com.yu.pl.app.challeng.notemark.core.presentation.util.getLayoutType
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType

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

    when(layoutType){
        LayoutType.PORTRAIT -> PortraitLandingScreen(onAction = action)
        LayoutType.LANDSCAPE -> LandscapeLandingScreen(onAction = action)
        LayoutType.TABLET -> TabletLandingScreen(onAction = action)
    }
}