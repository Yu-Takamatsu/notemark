package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListRoot(
    viewModel: NoteListViewModel = koinViewModel(),
    navigateToEditNote: (NoteMarkUi, NoteEditMode) -> Unit,
    navigateSettings:()->Unit
) {
    val layoutType = getLayoutType()
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action:(NoteListAction)-> Unit ={ action ->
        when(action){
            NoteListAction.OnClickSettings -> navigateSettings()
            else -> viewModel.onAction(action)
        }

    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is NoteListEvent.NavigateToEditScreen -> navigateToEditNote(event.note, event.editMode)
            }
        }
    }

    NoteListScreen(
        state = state.value,
        action = action,
        layoutType = layoutType
    )

    if (state.value.isShowDeleteConfirm && state.value.deleteNote != null) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onAction(NoteListAction.OnCancelDelete)
            },
            shape = RoundedCornerShape(4.dp),
            confirmButton = {
                NoteMarkButton(
                    label = stringResource(R.string.delete_note_confirm_confirm),
                    onClick = { viewModel.onAction(NoteListAction.OnDeleteNote) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            dismissButton = {
                NoteMarkButton(
                    label = stringResource(R.string.delete_note_confirm_cancel),
                    onClick = { viewModel.onAction(NoteListAction.OnCancelDelete) },
                    isLoading = state.value.isLoading,
                    enable = !state.value.isLoading
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.delete_note_confirm_title),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.delete_note_confirm_description),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
    }
    if (state.value.isLoading && !state.value.isShowDeleteConfirm) {
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