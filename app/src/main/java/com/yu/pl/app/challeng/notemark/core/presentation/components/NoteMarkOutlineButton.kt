package com.yu.pl.app.challeng.notemark.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun NoteMarkOutlineButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit = {},
    icon: (@Composable () -> Unit)? = null,
    iconPosition: Int = 0, // left:0, right:1
    enable: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier
            .height(48.dp)
            .alpha(if(enable) 1f else 0.38f),
        shape = RoundedCornerShape(12.dp),
        border = ButtonDefaults.outlinedButtonBorder(
            true
        ),
        contentPadding = PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = if(icon == null) 20.dp else if(iconPosition == 0) 16.dp else 20.dp,
            end = if(icon == null) 20.dp else if(iconPosition == 0) 20.dp else 16.dp
        ),
        enabled = enable,
        onClick = onClick
    ){

            if(icon != null && iconPosition == 0){
                icon()
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,

            )
            if(icon != null && iconPosition == 1){
                icon()
            }

    }
}