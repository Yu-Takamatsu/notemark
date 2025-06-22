package com.yu.pl.app.challeng.notemark.core.di

import androidx.room.Room
import com.yu.pl.app.challeng.notemark.core.data.AuthTokenDataStore
import com.yu.pl.app.challeng.notemark.core.data.InputAccountDataValidator
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDatabase
import com.yu.pl.app.challeng.notemark.core.domain.AccountValidator
import com.yu.pl.app.challeng.notemark.core.domain.AuthTokenRepository
import com.yu.pl.app.challeng.notemark.core.network.createHttpClient
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {
    factory {
        InputAccountDataValidator()
    }.bind(AccountValidator::class)
    factory {
        createHttpClient(androidContext())
    }.bind(HttpClient::class)
    single {
        AuthTokenDataStore(androidContext())
    }.bind(AuthTokenRepository::class)

    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteMarkDatabase::class.java,
            "note-mark.db"
        ).build()
    }

    single{
        get<NoteMarkDatabase>().noteMarkDao
    }


}