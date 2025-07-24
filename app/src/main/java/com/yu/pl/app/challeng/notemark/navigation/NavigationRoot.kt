package com.yu.pl.app.challeng.notemark.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yu.pl.app.challeng.notemark.features.presentation.note.editnote.EditNoteRoot
import com.yu.pl.app.challeng.notemark.features.presentation.landing.LandingRoot
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.LoginRoot
import com.yu.pl.app.challeng.notemark.features.presentation.models.NoteMarkUi
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.NoteListRoot
import com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.RegistrationRoot
import com.yu.pl.app.challeng.notemark.features.presentation.note.settings.SettingsRoot
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
    }

}