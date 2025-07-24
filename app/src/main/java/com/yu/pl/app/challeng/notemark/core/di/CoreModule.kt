package com.yu.pl.app.challeng.notemark.core.di

import androidx.room.Room
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.yu.pl.app.challeng.notemark.MyApplication
import com.yu.pl.app.challeng.notemark.core.api.data.RemoteNoteApi
import com.yu.pl.app.challeng.notemark.core.api.data.RemoteNoteApiPayloadCreator
import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApiPayloadCreator
import com.yu.pl.app.challeng.notemark.core.api.domain.NoteApi
import com.yu.pl.app.challeng.notemark.core.preference.data.PreferenceDataStore
import com.yu.pl.app.challeng.notemark.core.database.NoteMarkDatabase
import com.yu.pl.app.challeng.notemark.core.database.sync.NoteSyncQueueDatabase
import com.yu.pl.app.challeng.notemark.core.jobs.NoteDataSyncWorker
import com.yu.pl.app.challeng.notemark.core.jobs.data.RemoteNoteDataSyncService
import com.yu.pl.app.challeng.notemark.core.jobs.domain.NoteDataSyncJobExecutor
import com.yu.pl.app.challeng.notemark.core.preference.domain.PreferenceRepository
import com.yu.pl.app.challeng.notemark.core.api.createHttpClient
import com.yu.pl.app.challeng.notemark.core.api.data.RemoteAuthApi
import com.yu.pl.app.challeng.notemark.core.api.domain.AuthApi
import com.yu.pl.app.challeng.notemark.core.network.data.ConnectivityManager
import com.yu.pl.app.challeng.notemark.core.network.domain.ConnectivityRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule = module {

    single<CoroutineScope> {
        (androidApplication() as MyApplication).applicationScope
    }
    factory {
        createHttpClient(androidContext())
    }.bind(HttpClient::class)
    single {
        PreferenceDataStore(androidContext())
    }.bind(PreferenceRepository::class)

    factory {
        RemoteNoteApi(get())
    }.bind(NoteApi::class)

    factory {
        RemoteAuthApi(get())
    }.bind(AuthApi::class)

    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteMarkDatabase::class.java,
            "note-mark.db"
        ).build()
    }

    single {
        get<NoteMarkDatabase>().noteMarkDao
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            NoteSyncQueueDatabase::class.java,
            "note-sync-queue.dp"
        ).build()
    }
    single {
        get<NoteSyncQueueDatabase>().syncQueueDao
    }

    single<NoteApiPayloadCreator> {
        RemoteNoteApiPayloadCreator()
    }
    worker { (params: WorkerParameters) ->
        NoteDataSyncWorker(get(), params, get())
    }

    single<WorkManager> {
        WorkManager.getInstance(get())
    }
    factory {
        RemoteNoteDataSyncService(get(), get(), get(), get(), get())
    }.bind(NoteDataSyncJobExecutor::class)


    single {
        ConnectivityManager(get(), get())
    }.bind(ConnectivityRepository::class)


}