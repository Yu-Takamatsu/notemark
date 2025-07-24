package com.yu.pl.app.challeng.notemark.features.di

import com.yu.pl.app.challeng.notemark.core.api.data.RemoteNoteApi
import com.yu.pl.app.challeng.notemark.features.domain.auth.AccountValidator
import com.yu.pl.app.challeng.notemark.features.domain.auth.LoginUseCase
import com.yu.pl.app.challeng.notemark.features.domain.auth.RegisterAccountUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.CreateNoteUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.DeleteNoteUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.GetAllNotesUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.UpdateNoteUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.ChangeSyncIntervalUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.LogoutUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.SyncDataUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.SyncLogoutUseCase
import com.yu.pl.app.challeng.notemark.features.domain.note.settings.UnsyncLogoutUseCase
import com.yu.pl.app.challeng.notemark.features.presentation.note.editnote.EditNoteViewModel
import com.yu.pl.app.challeng.notemark.features.presentation.auth.login.LoginViewModel
import com.yu.pl.app.challeng.notemark.features.presentation.note.notelist.NoteListViewModel
import com.yu.pl.app.challeng.notemark.features.presentation.auth.registration.RegistrationViewModel
import com.yu.pl.app.challeng.notemark.features.presentation.note.settings.SettingsViewModel
import com.yu.pl.app.challeng.notemark.features.presentation.splash.SplashViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val FeaturesModule = module {

    factory {
        RemoteNoteApi(get())
    }

    factory {
        LoginUseCase(get(), get(), get())
    }

    factory {
        RegisterAccountUseCase(get())
    }
    factory {
        CreateNoteUseCase(get(), get(), get())
    }
    factory {
        DeleteNoteUseCase(get(), get(), get())
    }

    factory {
        GetAllNotesUseCase(get())
    }
    factory {
        UpdateNoteUseCase(get(), get(), get())
    }

    factory {
        ChangeSyncIntervalUseCase(get())
    }
    factory {
        LogoutUseCase(get(), get(), get())
    }
    factory {
        SyncDataUseCase(get())
    }
    factory {
        SyncLogoutUseCase(get(), get())
    }
    factory {
        UnsyncLogoutUseCase(get(), get(), get(), get())
    }

    factory {
        AccountValidator()
    }


    viewModelOf(::SplashViewModel)
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::EditNoteViewModel)
    viewModelOf(::SettingsViewModel)
}
