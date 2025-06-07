package com.yu.pl.app.challeng.notemark.functions.presentation.landing.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.presentation.components.NoteMarkButton
import com.yu.pl.app.challeng.notemark.core.presentation.components.NoteMarkOutlineButton
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.LandingAction
import com.yu.pl.app.challeng.notemark.ui.theme.LandingBg
import com.yu.pl.app.challeng.notemark.ui.theme.NoteMarkTheme

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun PortraitLandingScreen(
    onAction: (action: LandingAction) -> Unit,
) {

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = LandingBg
                )
        ) {
            Image(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.TopCenter),
                painter = painterResource(R.drawable.landing_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(322.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    ).padding(
                        top = 32.dp,
                        bottom = 40.dp
                    )
                    .padding(horizontal = 16.dp)

            ) {
                Text(
                    text = stringResource(R.string.landing_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = stringResource(R.string.landing_description),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.size(40.dp))
                NoteMarkButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Get Started",
                    onClick = {
                        onAction(LandingAction.OnGetStartClick)
                    }
                )
                Spacer(modifier = Modifier.size(12.dp))
                NoteMarkOutlineButton(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Log in",
                    onClick = {
                        onAction(LandingAction.OnLoginClick)
                    }
                )

            }

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PortraitLandingScreenPreview() {
    NoteMarkTheme {
        PortraitLandingScreen { }
    }
}