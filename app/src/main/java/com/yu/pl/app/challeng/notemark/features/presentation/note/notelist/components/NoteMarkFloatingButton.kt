package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.ui.theme.BgGradient


@Composable
fun NoteMarkFloatingButton(
    onClick : ()-> Unit,
    description:String,
){

    Box(
        modifier = Modifier.size(64.dp)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(
                brush = MaterialTheme.colorScheme.BgGradient
            ).clickable(
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.Add,
            contentDescription = description,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}