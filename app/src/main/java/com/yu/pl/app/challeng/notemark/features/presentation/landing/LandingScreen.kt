package com.yu.pl.app.challeng.notemark.features.presentation.landing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.yu.pl.app.challeng.notemark.R
import com.yu.pl.app.challeng.notemark.core.ui.util.model.LayoutType
import com.yu.pl.app.challeng.notemark.features.presentation.landing.components.LandingHeader
import com.yu.pl.app.challeng.notemark.features.presentation.landing.components.LandingMenu
import com.yu.pl.app.challeng.notemark.ui.theme.LandingBg

@Composable
fun LandingScreen(
    action:(LandingAction)->Unit,
    layoutType: LayoutType,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.waterfall
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = LandingBg)

        ) {
            when (layoutType) {
                LayoutType.PORTRAIT -> {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
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
                            )
                            .padding(
                                top = 32.dp,
                                bottom = 40.dp
                            )
                            .padding(horizontal = 16.dp)

                    ) {
                        LandingHeader(
                            modifier = Modifier.fillMaxWidth(),
                            isTable = false
                        )
                        Spacer(modifier = Modifier.size(40.dp))
                        LandingMenu(
                            modifier = Modifier.fillMaxWidth(),
                            onClickLogin = {
                                action(LandingAction.OnLoginClick)
                            },
                            onClickStart = {
                                action(LandingAction.OnGetStartClick)
                            }
                        )
                    }
                }

                LayoutType.LANDSCAPE -> {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.45f)
                            .windowInsetsPadding(WindowInsets.safeContent)
                            .align(Alignment.CenterStart),
                        painter = painterResource(R.drawable.landing_image),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(330.dp)
                            .align(Alignment.CenterEnd)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(
                                    topStart = 20.dp,
                                    bottomStart = 20.dp
                                )
                            )
                            .padding(
                                vertical = 40.dp
                            )
                            .padding(
                                start = 60.dp,
                                end = 40.dp
                            )

                    ) {
                        LandingHeader(
                            modifier = Modifier.fillMaxWidth(),
                            isTable = false
                        )
                        Spacer(modifier = Modifier.size(40.dp))
                        LandingMenu(
                            modifier = Modifier.fillMaxWidth(),
                            onClickLogin = {
                                action(LandingAction.OnLoginClick)
                            },
                            onClickStart = {
                                action(LandingAction.OnGetStartClick)
                            }
                        )
                    }
                }

                LayoutType.TABLET -> {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        painter = painterResource(R.drawable.landing_image),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(322.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = RoundedCornerShape(
                                    topStart = 24.dp,
                                    topEnd = 24.dp
                                )
                            )
                            .padding(
                                48.dp
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        LandingHeader(
                            modifier = Modifier.fillMaxWidth(),
                            isTable = true
                        )
                        Spacer(modifier = Modifier.size(40.dp))
                        LandingMenu(
                            modifier = Modifier.fillMaxWidth(),
                            onClickLogin = {
                                action(LandingAction.OnLoginClick)
                            },
                            onClickStart = {
                                action(LandingAction.OnGetStartClick)
                            }
                        )
                    }
                }
            }

        }
    }
}