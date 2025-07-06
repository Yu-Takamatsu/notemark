package com.yu.pl.app.challeng.notemark.functions.presentation.editnote

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.core.presentation.util.RequestScreenOrientation
import com.yu.pl.app.challeng.notemark.core.presentation.util.ScreenOrientation
import com.yu.pl.app.challeng.notemark.core.presentation.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components.EdiModeSelector
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components.EditNoteField
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components.EditNoteTopBar
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components.ViewerNoteField
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.components.ViewerNoteTopBar
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteEditMode
import kotlinx.coroutines.delay

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditNoteScreen(
    state: EditNoteState,
    action: (EditNoteAction) -> Unit,
    layoutType: LayoutType,
) {
    val focusRequester = remember { FocusRequester() }

    var isShowAdditionalUI by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(state.editMode) {
        when (state.editMode) {
            NoteEditMode.View -> {
                isShowAdditionalUI = true
            }

            NoteEditMode.Edit -> {
                isShowAdditionalUI = false
                focusRequester.requestFocus()
            }

            NoteEditMode.Reader -> {
                isShowAdditionalUI = true
            }
        }
    }
    LaunchedEffect(isShowAdditionalUI, state.editMode) {
        if (state.editMode == NoteEditMode.Reader && isShowAdditionalUI) {
            delay(5000)
            isShowAdditionalUI = false
        }
    }
    if (state.editMode == NoteEditMode.Reader) {
        RequestScreenOrientation(ScreenOrientation.Landscape)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        contentWindowInsets = WindowInsets.statusBars,
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.editMode != NoteEditMode.Edit && isShowAdditionalUI,
                enter = fadeIn(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    EdiModeSelector(
                        mode = state.editMode,
                        onChangeMode = { editMote ->
                            action(EditNoteAction.OnChangeEditMode(editMote))
                        }
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->

        val baseModifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(innerPadding)
            .imePadding()
            .padding(if (WindowInsets.isImeVisible) PaddingValues(0.dp) else WindowInsets.ime.asPaddingValues())
            .consumeWindowInsets(WindowInsets.statusBars)
            .let {
                if (state.editMode == NoteEditMode.Reader) {
                    it.pointerInput(Unit) {
                        detectTapGestures {
                            isShowAdditionalUI = !isShowAdditionalUI
                        }
                    }
                } else {
                    it
                }
            }

        when (layoutType) {
            LayoutType.PORTRAIT -> {
                Column(
                    modifier = baseModifier
                ) {
                    if (state.editMode == NoteEditMode.Edit) {
                        EditNoteTopBar(
                            modifier = Modifier.fillMaxWidth(),
                            onDiscard = { action(EditNoteAction.OnDiscard) },
                            onSaveNote = { action(EditNoteAction.OnSaveNote) }

                        )
                        EditNoteField(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                            onChangeContent = { value -> action(EditNoteAction.OnChangeContent(value)) },
                            focusRequester = focusRequester
                        )
                    } else {
                        if (isShowAdditionalUI) {
                            ViewerNoteTopBar(
                                modifier = Modifier.fillMaxWidth(),
                                onNavigateBack = {
                                    action(EditNoteAction.OnNavigateBack)
                                }
                            )
                        }
                        ViewerNoteField(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            isTable = false
                        )
                    }

                }
            }

            LayoutType.LANDSCAPE -> {
                Box(
                    modifier = baseModifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                ) {
                    if (state.editMode == NoteEditMode.Edit) {
                        EditNoteTopBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopCenter),
                            onDiscard = { action(EditNoteAction.OnDiscard) },
                            onSaveNote = { action(EditNoteAction.OnSaveNote) }

                        )
                        EditNoteField(
                            modifier = Modifier
                                .widthIn(max = 540.dp)
                                .align(Alignment.TopCenter),
                            state = state,
                            onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                            onChangeContent = { value -> action(EditNoteAction.OnChangeContent(value)) },
                            focusRequester = focusRequester
                        )
                    } else {
                        AnimatedVisibility(
                            visible = isShowAdditionalUI,
                            enter = fadeIn(
                                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                            )
                        ) {
                            ViewerNoteTopBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.TopCenter),
                                onNavigateBack = {
                                    action(EditNoteAction.OnNavigateBack)
                                }
                            )
                        }
                        ViewerNoteField(
                            modifier = Modifier
                                .widthIn(max = 540.dp)
                                .align(Alignment.TopCenter),
                            state = state,
                            isTable = false,
                            onStartScrolling = {
                                isShowAdditionalUI = false
                            }
                        )
                    }
                }
            }

            LayoutType.TABLET -> {
                Column(
                    modifier = baseModifier
                ) {
                    if (state.editMode == NoteEditMode.Edit) {
                        EditNoteTopBar(
                            modifier = Modifier.fillMaxWidth(),
                            onDiscard = { action(EditNoteAction.OnDiscard) },
                            onSaveNote = { action(EditNoteAction.OnSaveNote) }

                        )
                        EditNoteField(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            onChangeTitle = { value -> action(EditNoteAction.OnChangeTitle(value)) },
                            onChangeContent = { value -> action(EditNoteAction.OnChangeContent(value)) },
                            focusRequester = focusRequester
                        )
                    } else {
                        if (isShowAdditionalUI) {
                            ViewerNoteTopBar(
                                modifier = Modifier.fillMaxWidth(),
                                onNavigateBack = {
                                    action(EditNoteAction.OnNavigateBack)
                                }
                            )
                        }
                        ViewerNoteField(
                            modifier = Modifier.fillMaxWidth(),
                            state = state,
                            isTable = false
                        )
                    }
                }
            }
        }

    }
}