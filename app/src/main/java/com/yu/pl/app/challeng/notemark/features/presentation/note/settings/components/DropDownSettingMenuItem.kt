package com.yu.pl.app.challeng.notemark.features.presentation.note.settings.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R

@Composable
fun <T> DropDownSettingMenu(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    onSelectMenu: (T) -> Unit,
    currentSetting: MenuItem<T>,
    dropDownMenuList: List<MenuItem<T>>,
) {

    var isExpandMenu by rememberSaveable {
        mutableStateOf(false)
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(
                onClick = {
                    isExpandMenu = true
                }
            )
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = null,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = currentSetting.label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Box {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .rotate(180f),
                imageVector = ImageVector.vectorResource(R.drawable.icon_chevron),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DropdownMenu(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .widthIn(min = 190.dp),
                expanded = isExpandMenu,
                offset = DpOffset(0.dp, 20.dp),
                shape = RoundedCornerShape(16.dp),
                onDismissRequest = {
                    isExpandMenu = false
                },
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                shadowElevation = 12.dp,
            ) {
                dropDownMenuList.forEach { menu ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = menu.label,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = {
                            onSelectMenu(menu.value)
                            isExpandMenu = false
                        },
                        trailingIcon = {
                            if (menu == currentSetting) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.icon_check),
                                    contentDescription = "current setting is ${menu.label}",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            top = 10.dp,
                            bottom = 10.dp,
                            end = 12.dp
                        )
                    )
                }
            }
        }
    }

}

data class MenuItem<T>(val label: String, val value: T)