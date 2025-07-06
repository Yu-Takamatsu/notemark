package com.yu.pl.app.challeng.notemark.functions.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme
import com.yu.pl.app.challeng.notemark.ui.theme.titleXSmall

@Composable
fun SettingsScreen(
    action: (SettingsAction) -> Unit,
    layoutType: LayoutType,
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        val baseModifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(innerPadding)

        when (layoutType) {
            LayoutType.PORTRAIT -> {
                Column(
                    modifier = baseModifier
                ) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        layoutType = layoutType,
                        onClickBack = {
                            action(SettingsAction.OnBackNavigation)
                        }
                    )
                    MenuColum(
                        modifier = Modifier.fillMaxSize(),
                        layoutType = layoutType,
                        onClickLogout = {
                            action(SettingsAction.OnLogout)
                        }
                    )
                }
            }
            LayoutType.LANDSCAPE -> {
                Column(
                    modifier = baseModifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                ) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        layoutType = layoutType,
                        onClickBack = {
                            action(SettingsAction.OnBackNavigation)
                        }
                    )
                    MenuColum(
                        modifier = Modifier.fillMaxSize(),
                        layoutType = layoutType,
                        onClickLogout = {
                            action(SettingsAction.OnLogout)
                        }
                    )
                }
            }
            LayoutType.TABLET -> {
                Column(
                    modifier = baseModifier
                ) {
                    TopAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        layoutType = layoutType,
                        onClickBack = {
                            action(SettingsAction.OnBackNavigation)
                        }
                    )
                    MenuColum(
                        modifier = Modifier.fillMaxSize(),
                        layoutType = layoutType,
                        onClickLogout = {
                            action(SettingsAction.OnLogout)
                        }
                    )
                }
            }
        }

    }
}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    layoutType: LayoutType,
    onClickBack:()-> Unit
){
    Row(
        modifier = modifier
            .height(
                when(layoutType){
                    LayoutType.PORTRAIT -> 56.dp
                    LayoutType.LANDSCAPE -> 48.dp
                    LayoutType.TABLET -> 56.dp
                }
            ).padding(
                horizontal = when(layoutType){
                    LayoutType.PORTRAIT -> 16.dp
                    LayoutType.LANDSCAPE -> 16.dp
                    LayoutType.TABLET -> 24.dp
                },
                vertical = when(layoutType){
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
        ){
            IconButton(
                modifier = Modifier.size(20.dp),
                onClick = onClickBack
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "",
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


@Composable
fun MenuColum(
    modifier: Modifier = Modifier,
    layoutType: LayoutType,
    onClickLogout:()-> Unit
){
    Column(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = when(layoutType){
                LayoutType.PORTRAIT -> 16.dp
                LayoutType.LANDSCAPE -> 16.dp
                LayoutType.TABLET -> 24.dp
            })
    ) {
        Row(modifier = Modifier.fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = onClickLogout
            )
            .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_logout),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.error
            )
            Text(
                text = stringResource(R.string.settings_menu_logout),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape")
fun SettingsScreenPreview() {
    NoteMarkTheme {
        SettingsScreen(
            action = {},
            layoutType = LayoutType.LANDSCAPE
        )
    }
}