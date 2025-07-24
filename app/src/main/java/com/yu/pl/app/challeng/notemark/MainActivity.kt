package com.yu.pl.app.challeng.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.yu.pl.app.challeng.notemark.features.presentation.splash.SplashViewModel
import com.yu.pl.app.challeng.notemark.navigation.NavigationRoot
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme
import org.koin.java.KoinJavaComponent.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashViewModel = getKoin().get<SplashViewModel>()
        installSplashScreen().setKeepOnScreenCondition {
            splashViewModel.isShowSplash.value
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val destination = splashViewModel.startDestination.collectAsStateWithLifecycle().value
            NoteMarkTheme {
                if (destination != null) {
                    val navController = rememberNavController()
                    NavigationRoot(
                        navController = navController,
                        startDestination = destination,
                    )
                }
            }
        }
    }
}
