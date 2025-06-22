package com.yu.pl.app.challeng.notemark.functions.di

import com.yu.pl.app.challeng.notemark.functions.data.login.LoginApi
import com.yu.pl.app.challeng.notemark.functions.data.note.NoteMarkApi
import com.yu.pl.app.challeng.notemark.functions.data.note.NoteMarkDataSource
import com.yu.pl.app.challeng.notemark.functions.data.registration.RegistrationApi
import com.yu.pl.app.challeng.notemark.functions.domain.login.LoginRepository
import com.yu.pl.app.challeng.notemark.functions.domain.note.NoteMarkRepository
import com.yu.pl.app.challeng.notemark.functions.domain.registration.RegistrationRepository
import com.yu.pl.app.challeng.notemark.functions.presentation.editnote.EditNoteViewModel
import com.yu.pl.app.challeng.notemark.functions.presentation.landing.LandingViewModel
import com.yu.pl.app.challeng.notemark.functions.presentation.login.LoginViewModel
import com.yu.pl.app.challeng.notemark.functions.presentation.notelist.NoteListViewModel
import com.yu.pl.app.challeng.notemark.functions.presentation.registration.RegistrationViewModel
import com.yu.pl.app.challeng.notemark.functions.presentation.splash.SplashViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val FunctionsModule = module {

    factory {
        RegistrationApi(get())
    }.bind(RegistrationRepository::class)
    factory {
        LoginApi(get(), get())
    }.bind(LoginRepository::class)

    factory {
        NoteMarkApi(get(), CoroutineScope(Dispatchers.Default + SupervisorJob()), get())
    }

    factory {
        NoteMarkDataSource(get(), get())
    }.bind(NoteMarkRepository::class)

    viewModelOf(::LandingViewModel)
    viewModelOf(::SplashViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::EditNoteViewModel)
}
