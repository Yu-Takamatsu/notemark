package com.yu.pl.app.challeng.notemark.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    icon: (@Composable () -> Unit)? = null,
    iconPosition: Int = 0, // left:0, right:1
    enable: Boolean = true,
    isLoading: Boolean = false,
) {

    Button(
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = if(isLoading) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
        ),
        contentPadding = PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = if (icon == null) 20.dp else if (iconPosition == 0) 16.dp else 20.dp,
            end = if (icon == null) 20.dp else if (iconPosition == 0) 20.dp else 16.dp
        ),
        enabled = enable && !isLoading,
        onClick = onClick
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.aspectRatio(1f/1f).fillMaxHeight(),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            if (icon != null && iconPosition == 0) {
                icon()
                Spacer(modifier = Modifier.size(8.dp))
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,

                )
            if (icon != null && iconPosition == 1) {
                Spacer(modifier = Modifier.size(8.dp))
                icon()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NoteMarkButtonPreview() {
    NoteMarkTheme {
        NoteMarkButton(
            modifier = Modifier.width(109.dp),
            label = "Label",
            enable = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Brush,
                    contentDescription = ""
                )
            },
            iconPosition = 1,
            isLoading = true
        )
    }
}