package com.yu.pl.app.challeng.notemark.functions.presentation.editnote.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.EditNoteAction
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.EditNoteState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditNoteScreen(
    state: EditNoteState,
    action: (EditNoteAction) -> Unit,
    layoutType: LayoutType
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }


    Scaffold(
        modifier = Modifier.fillMaxSize()
            .imePadding(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->

        val baseModifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(innerPadding)
            .imePadding()
            .padding(if(WindowInsets.isImeVisible) PaddingValues(0.dp) else WindowInsets.ime.asPaddingValues())
            .consumeWindowInsets(WindowInsets.statusBars)

        Box(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest))
        when(layoutType){
            LayoutType.PORTRAIT -> {
                Column(
                    modifier = baseModifier
                ) {
                    EditNoteTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        onDiscard = {action(EditNoteAction.OnDiscard) },
                        onSaveNote = {action(EditNoteAction.OnSaveNote)}

                    )
                    EditField(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                        onChangeContent = {value -> action(EditNoteAction.OnChangeContent(value))},
                        focusRequester = focusRequester
                    )
                }
            }
            LayoutType.LANDSCAPE -> {
                Box(
                    modifier = baseModifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                ){
                    EditNoteTopBar(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.TopCenter),
                        onDiscard = {action(EditNoteAction.OnDiscard) },
                        onSaveNote = {action(EditNoteAction.OnSaveNote)}

                    )
                    EditField(
                        modifier = Modifier.widthIn(max = 540.dp)
                            .align(Alignment.TopCenter),
                        state = state,
                        onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                        onChangeContent = {value -> action(EditNoteAction.OnChangeContent(value))},
                        focusRequester = focusRequester
                    )
                }
            }
            LayoutType.TABLET -> {
                Column(
                    modifier = baseModifier
                ) {
                    EditNoteTopBar(
                        modifier = Modifier.fillMaxWidth(),
                        onDiscard = {action(EditNoteAction.OnDiscard) },
                        onSaveNote = {action(EditNoteAction.OnSaveNote)}

                    )
                    EditField(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                        onChangeContent = {value -> action(EditNoteAction.OnChangeContent(value))},
                        focusRequester = focusRequester
                    )
                }
            }
        }

    }
}


@Composable
fun EditNoteTopBar(
    modifier: Modifier = Modifier,
    onDiscard:()-> Unit,
    onSaveNote:()-> Unit
){
    Row(
        modifier = modifier.fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            //modifier = Modifier.padding(start = 16.dp),
            onClick = onDiscard//{ action(EditNoteAction.OnDiscard) }
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = "clear",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable(
                    onClick = onSaveNote//{ action(EditNoteAction.OnSaveNote) }
                ),
            text = stringResource(R.string.edit_note_save),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@Composable
fun EditField(
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
            onValueChange = onChangeTitle,//{ value -> action(EditNoteAction.OnChangeTitle(value)) },
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
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
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
            onValueChange = onChangeContent,//{ value -> action(EditNoteAction.OnChangeContent(value)) },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            decorationBox = @Composable { innerTextField ->

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
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