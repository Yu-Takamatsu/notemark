package com.yu.pl.app.challeng.notemark.features.presentation.auth.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.components.LoginForm
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.components.LoginHeader

@Composable
fun LoginScreen(
    state: LoginState,
    action: (LoginAction) -> Unit,
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
            )


        when (layoutType) {
            LayoutType.PORTRAIT -> {
                Column(
                    modifier = baseModifier
                ) {
                    LoginHeader(
                        modifier = Modifier.fillMaxWidth(),
                        isTable = false
                    )
                    Spacer(modifier = Modifier.size(60.dp))
                    LoginForm(
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
                    LoginHeader(
                        modifier = Modifier.weight(1f),
                        isTable = false
                    )
                    LoginForm(
                        modifier = Modifier.weight(1f),
                        state = state, action = action
                    )
                }
            }

            LayoutType.TABLET -> {
                Column(
                    modifier = baseModifier
                        .padding(top = 98.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginHeader(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        isTable = true
                    )
                    Spacer(modifier = Modifier.size(32.dp))
                    LoginForm(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        state = state,
                        action = action
                    )
                }
            }
        }

    }
}