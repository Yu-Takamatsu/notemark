package com.yu.pl.app.challeng.notemark.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.EditNoteRoot
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.LandingRoot
import com.yu.pl.app.challeng.notemark.functions.presentation.login.LoginRoot
import com.yu.pl.app.challeng.notemark.functions.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.NoteListRoot
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.RegistrationRoot
import com.yu.pl.app.challeng.notemark.functions.presentation.settings.SettingsRoot
import com.yu.pl.app.challeng.notemark.ui.theme.BgGradient
import com.yu.pl.app.challeng.notemark.ui.theme.titleXLarge
import kotlin.reflect.typeOf

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Destination,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Destination.Landing> {
            LandingRoot(
                onNavigateToLogin = {
                    navController.navigate(Destination.Login) {
                        popUpTo(Destination.Landing) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegistration = {
                    navController.navigate(Destination.Registration) {
                        popUpTo(Destination.Landing) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Destination.Login> {
            LoginRoot(
                navigateToRegistration = {
                    navController.navigate(Destination.Registration) {
                        popUpTo(Destination.Login) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                navigateAfterLogin = {
                    navController.navigate(Destination.NoteList) {
                        launchSingleTop = true
                        popUpTo(Destination.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<Destination.Registration> {
            RegistrationRoot(
                navigateToLogin = {
                    navController.navigate(Destination.Login) {
                        popUpTo(Destination.Registration) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable<Destination.NoteList> {
            NoteListRoot(
                navigateToEditNote = { note, mode ->
                    navController.navigate(
                        Destination.EditNote(
                            note = note,
                            initialEditMode = mode
                        )
                    ) {
                        restoreState = true
                    }
                },
                navigateSettings = {
                    navController.navigate(Destination.Settings)
                }
            )
        }
        composable<Destination.EditNote>(
            typeMap = mapOf(
                typeOf<NoteMarkUi>() to CustomNavType.noteMarkType
            )
        ) { backStackEntry ->
            val editNoteDestination = backStackEntry.toRoute<Destination.EditNote>()
            val note = editNoteDestination.note
            val mode = editNoteDestination.initialEditMode

            EditNoteRoot(
                editNote = note,
                initialMode = mode,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Destination.Settings> {
            SettingsRoot(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateLogin = {
                    navController.navigate(Destination.Login) {
                        popUpTo(Destination.NoteList) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable<Destination.Dummy> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = MaterialTheme.colorScheme.BgGradient
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hello",
                    style = MaterialTheme.typography.titleXLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }

}