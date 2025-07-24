package com.yu.pl.app.challeng.notemark.features.presentation.auth.registration

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType
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

    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { registrationEvent ->
            when(registrationEvent){
                is RegistrationEvent.ShowToastMessage -> {
                    Toast.makeText(context, registrationEvent.message.value(context), Toast.LENGTH_LONG).show()
                }
                RegistrationEvent.SuccessRegistration -> {}
            }
        }
    }
    RegistrationScreen(
        state = state,
        action = action,
        layoutType = layoutType
    )

}
