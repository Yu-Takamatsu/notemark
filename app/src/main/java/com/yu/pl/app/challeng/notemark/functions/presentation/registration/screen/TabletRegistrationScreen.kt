package com.yu.pl.app.challeng.notemark.functions.presentation.registration.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.domain.EmailValidation
import com.yu.pl.app.challeng.notemark.core.domain.PasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.RepeatPasswordValidation
import com.yu.pl.app.challeng.notemark.core.domain.UserNameValidation
import com.yu.pl.app.challeng.notemark.core.presentation.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.presentation.components.NoteMarkTextField
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.RegistrationAction
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.RegistrationState
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.model.ValidationModel
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

@Composable
fun TabletRegistrationScreen(
    state: RegistrationState,
    action: (RegistrationAction) -> Unit,
    snackBarState: SnackbarHostState? = null,
) {
    Scaffold(
        snackbarHost = {
            if (snackBarState != null) SnackbarHost(snackBarState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = innerPadding.calculateTopPadding()
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainerLowest,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    )
                    .padding(
                        vertical = 100.dp,
                        horizontal = 120.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.registration_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.registration_description),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        NoteMarkTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.userName,
                            onValueChange = { value ->
                                action(
                                    RegistrationAction.OnUserNameInput(
                                        value
                                    )
                                )
                            },
                            label = stringResource(R.string.username),
                            placeHolder = stringResource(R.string.username_placeholder),
                            supportText = stringResource(R.string.username_support),
                            isError = state.validate.userName != UserNameValidation.Empty && state.validate.userName != UserNameValidation.Valid,
                            errorText = when (state.validate.userName) {
                                UserNameValidation.Short -> stringResource(R.string.invalid_username_short)
                                UserNameValidation.Long -> stringResource(R.string.invalid_username_long)
                                UserNameValidation.Invalid -> "Include not supported charactor"
                                else -> null
                            }
                        )
                        NoteMarkTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.email,
                            onValueChange = { value -> action(RegistrationAction.OnEmailInput(value)) },
                            label = stringResource(R.string.login_email),
                            placeHolder = stringResource(R.string.login_email_placeholder),
                            isError = state.validate.email == EmailValidation.Invalid,
                            errorText = when (state.validate.email) {
                                EmailValidation.Invalid -> stringResource(R.string.invalid_email)
                                else -> null
                            }
                        )
                        NoteMarkTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.password,
                            onValueChange = { value ->
                                action(
                                    RegistrationAction.OnPasswordInput(
                                        value
                                    )
                                )
                            },
                            label = stringResource(R.string.login_password),
                            placeHolder = stringResource(R.string.login_password),
                            isPasswordInput = true,
                            showPassword = state.isVisiblePassword,
                            onToggleShowPassword = {
                                action(RegistrationAction.OnTogglePasswordShow)
                            },
                            supportText = stringResource(R.string.password_support),
                            isError = state.validate.password == PasswordValidation.Invalid,
                            errorText = when (state.validate.password) {
                                PasswordValidation.Invalid -> stringResource(R.string.invalid_password)
                                else -> null
                            }
                        )
                        NoteMarkTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.repeatPassword,
                            onValueChange = { value ->
                                action(
                                    RegistrationAction.OnRepeatPasswordInput(
                                        value
                                    )
                                )
                            },
                            label = stringResource(R.string.login_repeat_password),
                            placeHolder = stringResource(R.string.login_password),
                            isPasswordInput = true,
                            showPassword = state.isVisibleRepeatPassword,
                            onToggleShowPassword = {
                                action(RegistrationAction.OnToggleRepeatPasswordShow)
                            },
                            isError = state.validate.repeatPassword == RepeatPasswordValidation.Invalid,
                            errorText = when (state.validate.repeatPassword) {
                                RepeatPasswordValidation.Invalid -> stringResource(R.string.invalid_repeat_password)
                                else -> null
                            }
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    NoteMarkButton(
                        modifier = Modifier.fillMaxWidth(),
                        label = stringResource(R.string.login_title),
                        enable = state.isAllValid,
                        onClick = {
                            action(RegistrationAction.OnCreateAccount)
                        },
                        isLoading = state.isLoading
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clickable {
                                action(RegistrationAction.OnAlreadyHaveAccount)
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
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun TableRegistrationScreenPreview() {
    NoteMarkTheme {
        TabletRegistrationScreen(
            state = RegistrationState(
                userName = "test",
                email = "test",
                password = "test",
                repeatPassword = "test",
                validate = ValidationModel(
                    userName = UserNameValidation.Valid,
                    email = EmailValidation.Valid,
                    password = PasswordValidation.Valid,
                    repeatPassword = RepeatPasswordValidation.Valid
                ),
                isAllValid = true,
                isVisiblePassword = false,
                isVisibleRepeatPassword = false,
            ),
            action = {}
        )
    }
}