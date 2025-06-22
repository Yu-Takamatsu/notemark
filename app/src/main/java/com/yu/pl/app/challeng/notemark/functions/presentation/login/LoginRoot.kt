package com.yu.pl.app.challeng.notemark.functions.presentation.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.functions.presentation.login.screen.LandscapeLoginScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.login.screen.PortraitLoginScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.login.screen.TabletLoginScreen
import com.yu.pl.app.challeng.notemark.core.presentation.util.getLayoutType
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegistration:()->Unit,
    navigateAfterLogin:() -> Unit
) {
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action:(LoginAction)->Unit = { loginAction ->
        when(loginAction){
            LoginAction.OnNotHaveAccount -> navigateToRegistration()
            else -> viewModel.onAction(loginAction)
        }
    }

    val snackBarState = remember {
        SnackbarHostState()
    }
    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { loginEvent ->
            when(loginEvent){
                is LoginEvent.ShowSnackBar -> {
                    snackBarState.showSnackbar(
                        loginEvent.message.value(context)
                    )
                }
                LoginEvent.SuccessLogin -> navigateAfterLogin()
            }
        }
    }

    when (layoutType) {
        LayoutType.PORTRAIT -> PortraitLoginScreen(
            state = state.value,
            action = action,
            snackBarState = snackBarState
        )

        LayoutType.LANDSCAPE -> LandscapeLoginScreen(
            state = state.value,
            action = action,
            snackBarState = snackBarState
        )

        LayoutType.TABLET -> TabletLoginScreen(
            state = state.value,
            action = action,
            snackBarState = snackBarState
        )
    }
}