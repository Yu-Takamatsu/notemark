package com.yu.pl.app.challeng.notemark.features.presentation.auth.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.components.RegistrationForm
import com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.components.RegistrationHeader

@Composable
fun RegistrationScreen(
    state: RegistrationState,
    action: (RegistrationAction) -> Unit,
    layoutType: LayoutType,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars,
        containerColor = MaterialTheme.colorScheme.primary
    ) { innerPadding ->

        val baseModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = RoundedCornerShape(
                    topStart = 18.dp,
                    topEnd = 18.dp
                )
            )
            .consumeWindowInsets(WindowInsets.statusBars)
            .padding(
                top = 32.dp
            )
            .padding(
                horizontal = 16.dp
            ).fillMaxWidth(0.7f)

        when (layoutType) {
            LayoutType.PORTRAIT -> {
                Column(
                    modifier = baseModifier
                ) {
                    RegistrationHeader(
                        modifier = Modifier.fillMaxWidth(),
                        isTablet = false
                    )
                    Spacer(modifier = Modifier.size(40.dp))
                    RegistrationForm(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        action = action
                    )
                }
            }

            LayoutType.LANDSCAPE -> {
                Row(
                    modifier = baseModifier
                        .windowInsetsPadding(WindowInsets.safeContent)
                ) {
                    RegistrationHeader(
                        modifier = Modifier.weight(1f),
                        isTablet = false
                    )
                    RegistrationForm(
                        modifier = Modifier.weight(1f)
                            .verticalScroll(rememberScrollState()),
                        state = state,
                        action = action
                    )
                }
            }

            LayoutType.TABLET -> {
                Column(
                    modifier = baseModifier
                        .padding(top = 98.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RegistrationHeader(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        isTablet = true
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    RegistrationForm(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        state = state,
                        action = action
                    )
                }
            }
        }
    }
}