package com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R

@Composable
fun EditNoteTopBar(
    modifier: Modifier = Modifier,
    onDiscard:()-> Unit,
    onSaveNote:()-> Unit
){
    Row(
        modifier = modifier.fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = onDiscard
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = "navigate to back",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable(
                    onClick = onSaveNote
                ),
            text = stringResource(R.string.edit_note_save),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}