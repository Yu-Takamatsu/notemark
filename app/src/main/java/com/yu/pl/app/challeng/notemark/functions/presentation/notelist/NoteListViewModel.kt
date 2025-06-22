package com.yu.pl.app.challeng.notemark.functions.presentation.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.functions.domain.model.NoteMark
import com.yu.pl.app.challeng.notemark.functions.domain.note.NoteMarkRepository
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.functions.presentation.models.toNoteMark
import com.yu.pl.app.challeng.notemark.functions.presentation.models.toNoteMarkUi

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.util.UUID

class NoteListViewModel(
    private val noteRepository: NoteMarkRepository,
    private val authTokenRepository: AuthTokenRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.onStart {
        observeDataSource()
        getUserName()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NoteListState()
    )

    private val _event = Channel<NoteListEvent>()
    val event = _event.receiveAsFlow()

    fun onAction(action: NoteListAction) {
        when (action) {
            is NoteListAction.OnClickNote -> clickNote(action.noteMark)
            NoteListAction.OnCreateNoteAndNavigate -> createNoteAndNavigate()
            is NoteListAction.OnLongTapNote -> longTabNote(action.noteMark)
            NoteListAction.OnCancelDelete -> cancelDelete()
            is NoteListAction.OnDeleteNote -> deleteNote()
        }
    }

    private fun clickNote(noteMark: NoteMarkUi){
        navigateToEditScreen(noteMark)
    }

    private fun createNoteAndNavigate() {
        viewModelScope.launch {
            val currentTime = OffsetDateTime.now()
            val newNote = NoteMark(
                id = UUID.randomUUID(),
                title = "New Title",
                content = "",
                createdAt = currentTime,
                lastEditedAt = currentTime
            )
            navigateToEditScreen(newNote.toNoteMarkUi())
            noteRepository.createNote(newNote)
            _state.update { it.copy(isLoading = false) }

        }
    }

    private fun longTabNote(noteMark: NoteMarkUi){
        _state.update {
            it.copy(
                isShowDeleteConfirm = true,
                deleteNote = noteMark
            )
        }
    }

    private fun cancelDelete(){
        _state.update {
            it.copy(
                isShowDeleteConfirm = false,
                deleteNote = null
            )
        }
    }

    private fun deleteNote(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            _state.value.deleteNote?.let {
                noteRepository.deleteNote(it.toNoteMark())
            }
            _state.update { it.copy(isLoading = false, isShowDeleteConfirm = false, deleteNote = null) }
        }
    }

    private fun observeDataSource(){
        viewModelScope.launch {
            noteRepository.getNotes().map { list ->
                list.map {
                    println(it.toNoteMarkUi().toString())
                    it.toNoteMarkUi()
                }
            }.collect { list->
                _state.update {
                    it.copy(noteMarks = list)
                }
            }
        }
    }

    private fun navigateToEditScreen(noteMark: NoteMarkUi){
        viewModelScope.launch {
            _event.send(NoteListEvent.NavigateToEditScreen(noteMark))
        }
    }

    private fun getUserName(){
        viewModelScope.launch {
            val userName = authTokenRepository.getUserName()?:""
            _state.update {
                it.copy(userName = userName)
            }
        }
    }

}
