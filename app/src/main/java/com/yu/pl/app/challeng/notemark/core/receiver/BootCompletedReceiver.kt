package com.yu.pl.app.challeng.notemark.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yu.pl.app.challeng.notemark.core.jobs.NoteDataSyncWorker

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Intent.ACTION_BOOT_COMPLETED) return
        if (context == null) return

        val workManager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequestBuilder<NoteDataSyncWorker>()
            .setConstraints(
                Constraints(
                    requiredNetworkType = NetworkType.CONNECTED
                )
            ).build()
        workManager.enqueue(workRequest)
    }
}