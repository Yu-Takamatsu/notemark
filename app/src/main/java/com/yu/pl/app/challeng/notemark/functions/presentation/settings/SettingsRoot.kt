package com.yu.pl.app.challeng.notemark.functions.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.core.presentation.components.FullScreenProgressCircle
import com.yu.pl.app.challeng.notemark.core.presentation.util.getLayoutType
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onNavigateBack:() -> Unit
) {
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action:(SettingsAction)-> Unit = { action ->
        when(action){
            SettingsAction.OnBackNavigation -> onNavigateBack()
            SettingsAction.OnLogout -> viewModel.onAction(action)
        }

    }
    val event = viewModel.event

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                SettingsEvent.Error -> Unit
                SettingsEvent.NavigateLogin -> {
                    onNavigateLogin()
                }
            }
        }
    }

    SettingsScreen(
        action = action,
        layoutType = layoutType
    )
    if(state.value.isLoading){
        FullScreenProgressCircle()
    }
}