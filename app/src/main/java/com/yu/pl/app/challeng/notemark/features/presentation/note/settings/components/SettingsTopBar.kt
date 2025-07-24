package com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.ui.theme.titleXSmall

@Composable
fun SettingTopBar(
    modifier: Modifier = Modifier,
    layoutType: LayoutType,
    onClickBack: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(
                when (layoutType) {
                    LayoutType.PORTRAIT -> 56.dp
                    LayoutType.LANDSCAPE -> 48.dp
                    LayoutType.TABLET -> 56.dp
                }
            )
            .padding(
                horizontal = when (layoutType) {
                    LayoutType.PORTRAIT -> 16.dp
                    LayoutType.LANDSCAPE -> 16.dp
                    LayoutType.TABLET -> 24.dp
                },
                vertical = when (layoutType) {
                    LayoutType.PORTRAIT -> 8.dp
                    LayoutType.LANDSCAPE -> 12.dp
                    LayoutType.TABLET -> 8.dp
                }

            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.clickable(
                onClick = onClickBack
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onClickBack
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.settings_navigate_back),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = stringResource(R.string.settings_title).uppercase(),
                style = MaterialTheme.typography.titleXSmall.copy(
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}