package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yu.pl.app.challeng.notemark.core.ui.util.getLayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun EditNoteRoot(
    editNote: NoteMarkUi,
    initialMode: NoteEditMode,
    viewModel: EditNoteViewModel = koinViewModel {
        parametersOf(editNote, initialMode)
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

    val viewModelActions = { action: EditNoteAction ->
        if(action == EditNoteAction.OnNavigateBack){
            navigateBack()
        }else{
            viewModel.onAction(action)
        }

    }

    EditNoteScreen(
        state = state.value,
        action = viewModelActions,
        layoutType = layoutType
    )
}