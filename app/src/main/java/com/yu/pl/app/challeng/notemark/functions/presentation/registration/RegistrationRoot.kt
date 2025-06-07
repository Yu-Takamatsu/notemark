package com.yu.pl.app.challeng.notemark.functions.presentation.registration

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.screen.LandscapeRegistrationScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.screen.PortraitRegistrationScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.screen.TabletRegistrationScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.util.getLayoutType
import com.yu.pl.app.challeng.notemark.functions.presentation.util.model.LayoutType
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationRoot(
    viewModel: RegistrationViewModel = koinViewModel(),
    navigateToLogin:()-> Unit
) {

    val layoutType = getLayoutType()
    val action:(RegistrationAction)-> Unit = { action ->
        when(action){
            RegistrationAction.OnAlreadyHaveAccount -> navigateToLogin()
            else -> viewModel.onAction(action)
        }
    }
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val snackBarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { registrationEvent ->
            when(registrationEvent){
                is RegistrationEvent.ShowSnackBar -> {
                    snackBarState.showSnackbar(
                        registrationEvent.message.value(context)
                    )
                }
                RegistrationEvent.SuccessRegistration -> {}
            }
        }
    }

    when (layoutType) {
        LayoutType.PORTRAIT -> {
            PortraitRegistrationScreen(
                state = state,
                action = action,
                snackBarState = snackBarState
            )
        }

        LayoutType.LANDSCAPE -> {
            LandscapeRegistrationScreen(
                state = state,
                action = action,
                snackBarState = snackBarState
            )
        }

        LayoutType.TABLET -> {
            TabletRegistrationScreen(
                state = state,
                action = action,
                snackBarState = snackBarState
            )
        }
    }

}
