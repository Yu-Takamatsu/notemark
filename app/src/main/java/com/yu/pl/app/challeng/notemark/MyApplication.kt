package com.yu.pl.app.challeng.notemark

import android.app.Application
import androidx.room.Room
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDatabase
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDatabase
import com.yu.pl.app.challeng.notemark.core.di.coreModule
import com.yu.pl.app.challeng.notemark.features.di.FeaturesModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            workManagerFactory()
            modules(
                coreModule,
                FeaturesModule
            )
        }

        Room.databaseBuilder<NoteMarkDatabase>(
            this.applicationContext,
            NoteMarkDatabase::class.java,
            "note-mark.db"
        ).build()

        Room.databaseBuilder(
            this.applicationContext,
            NoteSyncQueueDatabase::class.java,
            "note-sync-queue.dp"
        ).build()



    }
}