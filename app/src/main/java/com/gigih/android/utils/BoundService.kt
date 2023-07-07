package com.gigih.android.utils

import android.app.Service
import android.content.Intent
import android.os.Binder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BoundService: Service() {

    private val binder = BinderDownload()

    override fun onBind(intent: Intent) = binder

    fun getProgress(): Flow<Int> {
        var progress = 0

        return flow {
            while (progress < 100) {
                progress += 10
                delay(1000)
                emit(progress)
            }
        }
    }

    inner class BinderDownload: Binder() {
        fun getService() = this@BoundService
    }
}