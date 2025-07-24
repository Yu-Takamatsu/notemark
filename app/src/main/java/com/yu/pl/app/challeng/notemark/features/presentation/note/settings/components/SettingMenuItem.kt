package com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SettingMenu(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    description: String? = null,
    onClick: () -> Unit,
    tintColor: Color? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(if (description.isNullOrBlank()) 56.dp else 78.dp)
            .clickable(
                onClick = onClick
            )
            .padding(vertical = 16.dp),
        verticalAlignment = if(description?.isNotBlank() == true)Alignment.Top else Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = null,
            tint = tintColor ?: Color.Unspecified
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = tintColor ?: MaterialTheme.colorScheme.onSurface
            )
            if (description?.isNotBlank() == true) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = tintColor ?: MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


