package com.yu.pl.app.challeng.notemark.features.presentation.note.settings

import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.components.FullScreenProgressCircle
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsRoot(
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateLogin: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action: (SettingsAction) -> Unit = { action ->
        when (action) {
            SettingsAction.OnBackNavigation -> onNavigateBack()
            else -> viewModel.onAction(action)
        }

    }
    val context = LocalContext.current
    val event = viewModel.event

    LaunchedEffect(event) {
        event.collect {
            when (it) {
                SettingsEvent.NavigateLogin -> {
                    onNavigateLogin()
                }

                is SettingsEvent.ShowToastMessage -> {
                    Toast.makeText(context, it.text.value(context), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    SettingsScreen(
        state = state.value,
        action = action,
        layoutType = layoutType
    )
    if (state.value.isLoading) {
        FullScreenProgressCircle()
    }
    if (state.value.isShowConfirmSyncInLogoutDialog) {
        AlertDialog(
            onDismissRequest = {
                action(SettingsAction.OnCancelSync)
            },
            shape = RoundedCornerShape(4.dp),
            confirmButton = {
                NoteMarkButton(
                    label = stringResource(R.string.settings_logout_with_sync),
                    onClick = { action(SettingsAction.OnSyncLogout) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            dismissButton = {
                NoteMarkButton(
                    label = stringResource(R.string.settings_logout_without_sync),
                    onClick = { action(SettingsAction.OnUnSyncLogout) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.settings_confirm_sync_title),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.settings_confirm_sync_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
    if (state.value.isShowSyncFailDialog) {
        AlertDialog(
            onDismissRequest = {
                action(SettingsAction.OnCancelSync)
            },
            shape = RoundedCornerShape(4.dp),
            confirmButton = {
                NoteMarkButton(
                    label = stringResource(R.string.settings_logout_without_sync),
                    onClick = { action(SettingsAction.OnUnSyncLogout) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            dismissButton = {

                NoteMarkButton(
                    label = stringResource(R.string.settings_logout_cancel),
                    onClick = { action(SettingsAction.OnCancelSync) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.settings_confirm_logout_continue_title),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(

                    text = stringResource(R.string.settings_confirm_logout_continue_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
}