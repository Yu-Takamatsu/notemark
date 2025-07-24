package com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType

@Composable
fun NoteListTopBar(
    modifier: Modifier = Modifier,
    userName: String,
    isOnLine: Boolean,
    layoutType: LayoutType,
    onClickSetting: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                vertical = 8.dp,
                horizontal = when (layoutType) {
                    LayoutType.PORTRAIT -> 16.dp
                    LayoutType.LANDSCAPE -> 16.dp
                    LayoutType.TABLET -> 24.dp
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.note_list_title),
            style = MaterialTheme.typography.titleMedium
        )
        if(!isOnLine) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_cloud_off),
                contentDescription = stringResource(R.string.offline),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = onClickSetting
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(R.drawable.icon_settings),
                contentDescription = stringResource(R.string.navigate_to_settings)
            )
        }
        ProfileIcon(
            userName,
        )
    }

}