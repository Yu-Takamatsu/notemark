package com.yu.pl.app.challeng.notemark.features.presentation.landing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.ui.components.NoteMarkOutlineButton

@Composable
fun LandingMenu(
    modifier: Modifier,
    onClickLogin:()->Unit,
    onClickStart:()->Unit
){
    Column(modifier = modifier.fillMaxWidth()) {
        NoteMarkButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.landing_start),
            onClick = onClickStart
        )
        Spacer(modifier = Modifier.size(12.dp))
        NoteMarkOutlineButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.landing_log_in),
            onClick = onClickLogin
        )
    }
}