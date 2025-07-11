package com.yu.pl.app.challeng.notemark

import android.app.Application
import androidx.room.Room
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDatabase
import com.yu.pl.app.challeng.notemark.core.di.coreModule
import com.yu.pl.app.challeng.notemark.functions.di.FunctionsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                coreModule,
                FunctionsModule
            )
        }

        Room.databaseBuilder<NoteMarkDatabase>(
            this.applicationContext,
            NoteMarkDatabase::class.java,
            "note-mark.db"
        ).build()

    }
}