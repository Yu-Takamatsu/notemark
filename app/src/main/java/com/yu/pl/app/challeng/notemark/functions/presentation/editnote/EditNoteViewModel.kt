package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.functions.domain.note.NoteMarkRepository
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.functions.presentation.models.toNoteMark
import com.yu.pl.app.challeng.notemark.functions.presentation.models.toNoteMarkUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime

class EditNoteViewModel(
    private val noteMarkRepository: NoteMarkRepository,
    noteMark: NoteMarkUi,
    initialMode: NoteEditMode
) : ViewModel() {

    private val _state = MutableStateFlow(
        EditNoteState(
            originalNote = noteMark,
            title = noteMark.title,
            content = noteMark.content,
            isEdit = false,
            isShowConfirmDiscard = false,
            isLoading = false,
            editMode = initialMode
        )
    )

    private var hasLoadedInit = false

    val state = _state.onStart {
        if(!hasLoadedInit) {
            observerInput()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        EditNoteState(
            originalNote = noteMark,
            title = noteMark.title,
            content = noteMark.content,
            isEdit = false,
            isShowConfirmDiscard = false,
            isLoading = false
        )
    )

    private val _event = Channel<EditNoteEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: EditNoteAction) {
        when (action) {
            EditNoteAction.OnCancelDiscard -> {
                cancelDiscard()
            }

            is EditNoteAction.OnChangeContent -> {
                changeContent(action.content)
            }

            is EditNoteAction.OnChangeTitle -> {
                changeTitle(action.title)
            }

            EditNoteAction.OnDiscard -> {
                discard()
            }

            is EditNoteAction.OnSaveNote -> {
                saveNote()
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

            val updateNote = _state.value.originalNote.copy(
                title = _state.value.title,
                content = _state.value.content,
                lastEditedAt = OffsetDateTime.now().toEpochSecond()
            ).toNoteMark()

            val result = noteMarkRepository.updateNote(updateNote)
            if (result.isSuccess) {
                _state.update {
                    it.copy(
                        originalNote = updateNote.toNoteMarkUi(),
                        title = updateNote.title,
                        content = updateNote.content,
                        isEdit = false,
                        isLoading = false,
                        editMode = NoteEditMode.View
                    )
                }
            }else{
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun cancelDiscard() {
        _state.update {
            it.copy(
                isShowConfirmDiscard = false
            )
        }
    }

    private fun discard() {
        if (!_state.value.isEdit || _state.value.isShowConfirmDiscard) {
            _state.update {
                EditNoteState(
                    originalNote = it.originalNote,
                    title = it.originalNote.title,
                    content = it.originalNote.content,
                    isEdit = false,
                    isShowConfirmDiscard = false,
                    isLoading = false
                )
            }
        } else {
            _state.update {
                it.copy(isShowConfirmDiscard = true)
            }
        }
    }

    private fun observerInput() {
        combine(
            _state.map { it.title },
            _state.map { it.content }
        ) { title, content ->
            _state.update {
                it.copy(
                    isEdit = it.originalNote.title != title || it.originalNote.content != content
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun changeEditMode(mode: NoteEditMode) {
        _state.update {
            it.copy(
                editMode = mode
            )
        }
    }
}
