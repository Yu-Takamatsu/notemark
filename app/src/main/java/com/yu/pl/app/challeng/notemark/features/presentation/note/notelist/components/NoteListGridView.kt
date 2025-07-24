package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

@Composable
fun NoteListGridView(
    notes: List<NoteMarkUi>,
    layoutType: LayoutType,
    onClickNote: (NoteMarkUi) -> Unit,
    onLongTapNote: (NoteMarkUi) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(
            when (layoutType) {
                LayoutType.PORTRAIT -> 2
                LayoutType.LANDSCAPE -> 3
                LayoutType.TABLET -> 2
            }
        ),
        contentPadding = PaddingValues(
            top = 16.dp,
            start = when (layoutType) {
                LayoutType.PORTRAIT -> 16.dp
                LayoutType.LANDSCAPE -> 16.dp
                LayoutType.TABLET -> 24.dp
            },
            end = when (layoutType) {
                LayoutType.PORTRAIT -> 16.dp
                LayoutType.LANDSCAPE -> 16.dp
                LayoutType.TABLET -> 24.dp
            }
        ),
        verticalItemSpacing = 16.dp,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = notes,
            key = { note -> note.id }
        ) { noteMark ->
            NoteMarkCard(
                noteMark = noteMark,
                onClick = { onClickNote(noteMark) },
                onLongTap = { onLongTapNote(noteMark) },
                maxCharCount = 150
            )
        }
    }
}