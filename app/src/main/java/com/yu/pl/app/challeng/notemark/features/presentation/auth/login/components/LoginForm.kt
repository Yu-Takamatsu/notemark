package com.yu.pl.app.challeng.notemark.features.presentation.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkTextField
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.LoginAction
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.LoginState

@Composable
fun LoginForm(
    modifier: Modifier,
    state: LoginState,
    action: (LoginAction) -> Unit,
) {
    Column(modifier = modifier) {
        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            onValueChange = { value -> action(LoginAction.OnChangeEmailValue(value)) },
            label = stringResource(R.string.login_email),
            placeHolder = stringResource(R.string.login_email_placeholder)
        )
        Spacer(modifier = Modifier.size(16.dp))

        NoteMarkTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            onValueChange = { value -> action(LoginAction.OnChangePasswordValue(value)) },
            label = stringResource(R.string.login_password),
            placeHolder = stringResource(R.string.login_password),
            isPasswordInput = true,
            showPassword = state.isVisiblePassword,
            onToggleShowPassword = {
                action(LoginAction.OnTogglePasswordVisible)
            }
        )
        Spacer(modifier = Modifier.size(24.dp))

        NoteMarkButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.login_title),
            onClick = {
                action(LoginAction.OnLogin)
            },
            enable = state.validLogin,
            isLoading = state.isLoading
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable {
                    action(LoginAction.OnNotHaveAccount)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login_no_account),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
        }
    }
}