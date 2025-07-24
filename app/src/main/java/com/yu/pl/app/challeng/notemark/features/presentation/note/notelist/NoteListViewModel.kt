package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import com.yu.pl.app.challeng.notemark.core.network.domain.ConnectivityRepository
import com.yu.pl.app.challeng.notemark.features.domain.note.CreateNoteUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.DeleteNoteUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.GetAllNotesUseCase
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteEditMode
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.features.presentation.models.toNoteMark
import com.yu.pl.app.challeng.notemark.features.presentation.models.toNoteMarkUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val preferenceRepository: PreferenceRepository,
    private val createNoteUseCase: CreateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val networkConnectivityRepository: ConnectivityRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(NoteListState())
    val state = _state.onStart {
        observeDataSource()
        getUserName()
        observeConnectivity()

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
            NoteListAction.OnClickSettings -> Unit
        }
    }

    private fun clickNote(noteMark: NoteMarkUi) {
        navigateToEditScreen(noteMark, NoteEditMode.View)
    }

    private fun createNoteAndNavigate() {
        viewModelScope.launch {

            val createResult = createNoteUseCase.invoke()
            if (createResult.isFailure) {
                return@launch
            }
            val newNote = createResult.getOrThrow()
            navigateToEditScreen(newNote.toNoteMarkUi(), NoteEditMode.Edit)

        }
    }

    private fun longTabNote(noteMark: NoteMarkUi) {
        _state.update {
            it.copy(
                isShowDeleteConfirm = true,
                deleteNote = noteMark
            )
        }
    }

    private fun cancelDelete() {
        _state.update {
            it.copy(
                isShowDeleteConfirm = false,
                deleteNote = null
            )
        }
    }

    private fun deleteNote() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            _state.value.deleteNote?.let {
                deleteNoteUseCase(it.toNoteMark())
            }
            _state.update {
                it.copy(
                    isLoading = false,
                    isShowDeleteConfirm = false,
                    deleteNote = null
                )
            }
        }
    }

    private fun observeDataSource() {
        viewModelScope.launch {
            getAllNotesUseCase().map { list ->
                list.map {
                    it.toNoteMarkUi()
                }
            }.collect { list ->
                _state.update {
                    it.copy(noteMarks = list)
                }
            }
        }
    }

    private fun navigateToEditScreen(noteMark: NoteMarkUi, editMode: NoteEditMode) {
        viewModelScope.launch {
            _event.send(NoteListEvent.NavigateToEditScreen(noteMark, editMode))
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            val userName = preferenceRepository.getUserName() ?: ""
            _state.update {
                it.copy(userName = userName)
            }
        }
    }

    private fun observeConnectivity() {
        viewModelScope.launch {
            networkConnectivityRepository.internetConnection.collect { connection ->
                _state.update {
                    it.copy(
                        isOnline = connection
                    )
                }
            }
        }
    }

}
