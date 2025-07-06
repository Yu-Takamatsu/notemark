package com.yu.pl.app.challeng.notemark.functions.presentation.notelist

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
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteListEmptyView
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteListGridView
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteListTopBar
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteMarkFloatingButton

@Composable
fun NoteListScreen(
    state: NoteListState,
    action: (NoteListAction) -> Unit,
    layoutType: LayoutType,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            NoteMarkFloatingButton { action(NoteListAction.OnCreateNoteAndNavigate) }
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
                layoutType = layoutType
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