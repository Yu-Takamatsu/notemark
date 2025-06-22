package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.presentation.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.presentation.util.getLayoutType
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.screen.EditNoteScreen
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditNoteRoot(
    editNote: NoteMarkUi,
    viewModel: EditNoteViewModel = koinViewModel {
        parametersOf(editNote)
    },
    navigateBack: () -> Unit,
) {
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                EditNoteEvent.NavigateBack -> navigateBack()
            }
        }
    }

    EditNoteScreen(
        state = state.value,
        action = viewModel::onAction,
        layoutType = layoutType
    )


    //Confirm Delete
    if (state.value.isShowConfirmDiscard) {
        AlertDialog(
            onDismissRequest = {},
            shape = RoundedCornerShape(4.dp),
            confirmButton = {
                NoteMarkButton(
                    label = stringResource(R.string.discard_update_confirm),
                    onClick = { viewModel.onAction(EditNoteAction.OnDiscard) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            dismissButton = {

                NoteMarkButton(
                    label = stringResource(R.string.discard_update_cancel),
                    onClick = { viewModel.onAction(EditNoteAction.OnCancelDiscard) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.discard_update_titile),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.discard_update_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
    if (state.value.isLoading && !state.value.isShowConfirmDiscard) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
        }
    }
}