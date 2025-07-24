package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType

@Composable
fun NoteListEmptyView(
    modifier: Modifier = Modifier,
    layoutType: LayoutType
){
    Box(
        modifier = modifier.fillMaxSize()
            .padding(
                vertical = 80.dp,
                horizontal = when(layoutType){
                    LayoutType.PORTRAIT -> 16.dp
                    LayoutType.LANDSCAPE -> 16.dp
                    LayoutType.TABLET -> 24.dp
                }
            ),
        contentAlignment = Alignment.TopCenter
    ){
        Text(
            text = stringResource(R.string.note_list_empty),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}