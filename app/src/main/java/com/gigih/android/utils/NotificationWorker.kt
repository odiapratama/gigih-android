package com.gigih.android.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationBuilder(context).build()

        return Result.success()
    }
}