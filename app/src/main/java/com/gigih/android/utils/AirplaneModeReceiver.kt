package com.gigih.android.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneModeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneMode = intent?.getBooleanExtra("state", false) ?: return
        if (isAirplaneMode) context?.showToast("Airplane mode enabled")
        else context?.showToast("Airplane mode disabled")
    }
}