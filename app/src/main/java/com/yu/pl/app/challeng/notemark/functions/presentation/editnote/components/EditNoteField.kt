package com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.EditNoteState

@Composable
fun EditNoteField(
    modifier: Modifier = Modifier,
    state: EditNoteState,
    onChangeTitle:(value:String)-> Unit,
    onChangeContent:(value:String)->Unit,
    focusRequester: FocusRequester,
    isTable: Boolean = false
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),

        ) {
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .focusRequester(focusRequester),
            value = state.title,
            onValueChange = onChangeTitle,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            decorationBox = @Composable { innerTextField ->

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = if(isTable) 24.dp else 16.dp, vertical = 20.dp)
                ) {
                    if (state.title.isEmpty()) {
                        Text(
                            text = stringResource(R.string.note_edit_title_placeholder),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        )
                    }
                    innerTextField()
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
        Spacer(
            modifier = Modifier.height(1.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface
                )
        )
        BasicTextField(
            modifier = Modifier.fillMaxSize(),
            value = state.content,
            onValueChange = onChangeContent,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            decorationBox = @Composable { innerTextField ->

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    if (state.content.isEmpty()) {
                        Text(
                            text = stringResource(R.string.note_edit_content_placeholder),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.5f)
                            )
                        )
                    }
                    innerTextField()
                }
            },
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
        )
    }
}