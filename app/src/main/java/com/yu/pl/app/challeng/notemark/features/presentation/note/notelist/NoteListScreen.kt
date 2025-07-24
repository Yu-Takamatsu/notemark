package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.SetStatusBarIconsColor
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components.NoteListEmptyView
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components.NoteListGridView
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components.NoteListTopBar
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components.NoteMarkFloatingButton

@Composable
fun NoteListScreen(
    state: NoteListState,
    action: (NoteListAction) -> Unit,
    layoutType: LayoutType,
) {

    SetStatusBarIconsColor(true)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            NoteMarkFloatingButton(
                onClick = { action(NoteListAction.OnCreateNoteAndNavigate) },
                description = stringResource(R.string.note_list_create_note)
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(
                    top = innerPadding.calculateTopPadding()
                )
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            NoteListTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
                    .let {
                        when(layoutType){
                            LayoutType.PORTRAIT -> it
                            LayoutType.LANDSCAPE -> {
                                it.windowInsetsPadding(WindowInsets.displayCutout)
                            }
                            LayoutType.TABLET -> it
                        }
                    },
                userName = state.userName,
                onClickSetting = { action(NoteListAction.OnClickSettings) },
                layoutType = layoutType,
                isOnLine = state.isOnline
            )
            Column(
                modifier = Modifier.fillMaxSize()
                    .let {
                        when(layoutType){
                            LayoutType.PORTRAIT -> it
                            LayoutType.LANDSCAPE -> {
                                it.windowInsetsPadding(WindowInsets.displayCutout)
                            }
                            LayoutType.TABLET -> it
                        }
                    }
            ) {

                if (state.noteMarks.isEmpty()) {
                    NoteListEmptyView(
                        modifier = Modifier.fillMaxSize(),
                        layoutType = layoutType
                    )
                    return@Column
                }

                NoteListGridView(
                    notes = state.noteMarks,
                    layoutType = layoutType,
                    onClickNote = { note -> action(NoteListAction.OnClickNote(note)) },
                    onLongTapNote = { note ->
                        action(NoteListAction.OnLongTapNote(note))
                    }
                )
            }
        }
    }
}