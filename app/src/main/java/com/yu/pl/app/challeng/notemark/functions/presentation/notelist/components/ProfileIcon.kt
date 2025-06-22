package com.yu.pl.app.challeng.notemark.functions.presentation.notelist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileIcon(
    userName: String,
) {

    val splitNames = userName.split(" ").filter {
        it.isNotBlank()
    }
    val iconText = when (splitNames.size) {
        0 -> {
            ""
        }
        1 -> {
            if(userName.length < 2){
                userName
            }else {
                userName.substring(0 until 2)
            }
        }
        else -> "${splitNames.first().first()}${splitNames.last().first()}"
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .aspectRatio(1f / 1f)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = iconText.uppercase(),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W700)
        )
    }
}
