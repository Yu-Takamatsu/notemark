package com.yu.pl.app.challeng.notemark.features.presentation.note.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.features.domain.note.UpdateNoteUseCase
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.features.presentation.models.toNoteMark
import com.yu.pl.app.challeng.notemark.features.presentation.models.toNoteMarkUi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val updateNoteUseCase: UpdateNoteUseCase,
    initialNoteMark: NoteMarkUi,
    initialMode: NoteEditMode,
) : ViewModel() {


    private val _state = MutableStateFlow(
        EditNoteState(
            originalNote = initialNoteMark,
            title = initialNoteMark.title,
            content = initialNoteMark.content,
            isLoading = false,
            editMode = initialMode
        )
    )

    private var hasLoadedInit = false

    val state = _state.onStart {
        if (!hasLoadedInit) {
            observerInput()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EditNoteState(
            originalNote = initialNoteMark,
            title = initialNoteMark.title,
            content = initialNoteMark.content,
            isLoading = false
        )
    )

    private val _event = Channel<EditNoteEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: EditNoteAction) {
        when (action) {
            is EditNoteAction.OnChangeContent -> {
                changeContent(action.content)
            }

            is EditNoteAction.OnChangeTitle -> {
                changeTitle(action.title)
            }
            is EditNoteAction.OnChangeEditMode -> {
                changeEditMode(action.editMode)
            }
            EditNoteAction.OnNavigateBack -> Unit
        }
    }

    private fun changeContent(content: String) {
        _state.update {
            it.copy(content = content)
        }
    }

    private fun changeTitle(title: String) {
        _state.update {
            it.copy(title = title)
        }
    }

    private fun saveNote() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            val updateResult = updateNoteUseCase.invoke(
                _state.value.originalNote.toNoteMark(),
                _state.value.title,
                _state.value.content
            )

            if (updateResult.isSuccess) {

                val updateNote = updateResult.getOrThrow()
                _state.update {
                    it.copy(
                        originalNote = updateNote.toNoteMarkUi(),
                        title = updateNote.title,
                        content = updateNote.content,
                        isLoading = false,
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observerInput() {
        viewModelScope.launch {
            combine(
                _state.map { it.title },
                _state.map { it.content }
            ) { title, content ->
                _state.value.originalNote.title !== title || _state.value.originalNote.content !== content
            }.distinctUntilChanged().debounce(1000).collect { isEdit ->
                if(isEdit) saveNote()
            }
        }
    }

    private fun changeEditMode(mode: NoteEditMode) {
        _state.update {
            it.copy(
                editMode = mode
            )
        }
    }
}
