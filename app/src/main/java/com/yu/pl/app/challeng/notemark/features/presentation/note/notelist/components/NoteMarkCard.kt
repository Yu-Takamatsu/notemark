package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteMarkCard(
    noteMark: NoteMarkUi,
    onClick: () -> Unit,
    onLongTap: () -> Unit,
    maxCharCount: Int,
) {

    val contentText by remember(noteMark.content) {
        mutableStateOf(
            if (noteMark.content.length > maxCharCount) {
                noteMark.content.substring(0, maxCharCount) + "..."
            } else {
                noteMark.content
            }
        )
    }


    Card(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongTap
            ),

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = noteMark.createDataSummary,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = noteMark.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = contentText,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}