package com.yu.pl.app.challeng.notemark.features.presentation.landing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.ui.theme.titleXLarge

@Composable
fun LandingHeader(
    modifier: Modifier,
    isTable: Boolean,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.landing_title),
            style = if (isTable) MaterialTheme.typography.titleXLarge else MaterialTheme.typography.titleLarge,
            textAlign = if (isTable) TextAlign.Center else TextAlign.Start
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = stringResource(R.string.landing_description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = if (isTable) TextAlign.Center else TextAlign.Start
        )
    }
}