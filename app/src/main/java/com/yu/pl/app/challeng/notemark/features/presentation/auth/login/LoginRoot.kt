package com.yu.pl.app.challeng.notemark.features.presentation.auth.login

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.core.ui.util.SetStatusBarIconsColor
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginRoot(
    viewModel: LoginViewModel = koinViewModel(),
    navigateToRegistration: () -> Unit,
    navigateAfterLogin: () -> Unit,
) {
    SetStatusBarIconsColor(false)
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action: (LoginAction) -> Unit = { loginAction ->
        when (loginAction) {
            LoginAction.OnNotHaveAccount -> navigateToRegistration()
            else -> viewModel.onAction(loginAction)
        }
    }

    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { loginEvent ->
            when (loginEvent) {
                is LoginEvent.ShowToastMessage -> {
                    Toast.makeText(context, loginEvent.message.value(context), Toast.LENGTH_LONG)
                        .show()
                }

                LoginEvent.SuccessLogin -> navigateAfterLogin()
            }
        }
    }

    LoginScreen(
        state = state.value,
        action = action,
        layoutType = layoutType
    )
}