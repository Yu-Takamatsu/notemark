package com.yu.pl.app.challeng.notemark.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

@Composable
fun NoteMarkTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
    placeHolder: String? = null,
    label: String = "",
    supportText: String? = null,
    isError: Boolean = false,
    errorText: String? = null,
    singleLine: Boolean = true,
    isPasswordInput: Boolean = false,
    showPassword: Boolean = false,
    onToggleShowPassword: () -> Unit = {},
) {

    var hasFocus by remember{
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged{ focusState ->
                    hasFocus = focusState.hasFocus
                },
            value = value,
            onValueChange = onValueChange,
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                errorContainerColor = Color.Transparent,
                errorIndicatorColor = if(!hasFocus) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedIndicatorColor = Color.Transparent,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
            ),
            placeholder = {
                if (placeHolder?.isNotBlank() == true)
                    Text(
                        style = MaterialTheme.typography.bodyLarge,
                        text = placeHolder,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
            },
            visualTransformation = if (!isPasswordInput) VisualTransformation.None else if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isPasswordInput) {
                    IconButton(
                        onClick = {
                            onToggleShowPassword()
                        }
                    ) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if(showPassword) stringResource(R.string.unvisible_password)  else stringResource(R.string.show_password),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }else{
                    null
                }
            },
            isError = isError,
            shape = RoundedCornerShape(12.dp),
            singleLine = singleLine
        )
        if (hasFocus && supportText?.isNotBlank() == true) {
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = supportText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if(!hasFocus && isError && errorText?.isNotBlank() == true){
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = errorText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteMarkTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkTextField(
            value = "aaaa",
            label = "Label",
            onValueChange = {},
            placeHolder = "ohoge",
            supportText = "Supporting Text",
            isError = false,
            isPasswordInput = true,
            showPassword = false
        )
    }
}