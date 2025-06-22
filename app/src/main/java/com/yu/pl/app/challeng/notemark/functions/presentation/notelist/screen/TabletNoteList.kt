package com.yu.pl.app.challeng.notemark.functions.presentation.notelist.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.NoteListAction
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.NoteListState
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteMarkCard
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.NoteMarkFloatingButton
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components.ProfileIcon
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabletNoteListScreen(
    state: NoteListState,
    action:(NoteListAction)->Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        modifier = Modifier.padding(horizontal = 28.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.note_list_title),
                            style = MaterialTheme.typography.titleMedium
                        )
                        ProfileIcon(
                            state.userName,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        },
        floatingActionButton = {
            NoteMarkFloatingButton {action(NoteListAction.OnCreateNoteAndNavigate) }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(WindowInsets.statusBars),
        ){
            if(state.noteMarks.isEmpty()){
                Text(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            horizontal = 24.dp,
                            vertical = 80.dp
                        ),
                    text = stringResource(R.string.note_list_empty),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                return@Box
            }

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 24.dp,
                    end = 24.dp
                ),
                verticalItemSpacing = 16.dp,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = state.noteMarks,
                    key = { noteMark -> noteMark.id}
                ) { noteMark ->
                    NoteMarkCard(
                        noteMark = noteMark,
                        onClick = {action(NoteListAction.OnClickNote(noteMark))},
                        onLongTap = {action(NoteListAction.OnLongTapNote(noteMark))},
                        maxCharCount = 150
                    )
                }
            }

        }
    }

}


@Preview(showSystemUi = true, showBackground = true, apiLevel = 31)
@Composable
fun TabletNoteListScreenPreview(){
    NoteMarkTheme {
        /*TabletNoteListScreen(
            state = NoteListState(
                userName = "Yu Takmaatsu",
                noteMarks = (1..10).map { it ->
                    NoteMarkUi(
                        id = "$it",
                        title = "title $it",
                        content = (1..it).joinToString(",") { "content $it" },
                        dateText = "date",
                        createAt = OffsetDateTime.now(),
                        lastEditedAt = OffsetDateTime.now()
                    )
                }
            ),
            action = {}
        )*/
    }
}