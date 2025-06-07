package com.yu.pl.app.challeng.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.yu.pl.app.challeng.notemark.functions.presentation.splash.SplashViewModel
import com.yu.pl.app.challeng.notemark.navigation.NavigationRoot
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashViewModel = SplashViewModel()
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isShowSplash.value
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteMarkTheme {
                val navController = rememberNavController()
                NavigationRoot(
                    navController = navController
                )
            }
        }
    }
}