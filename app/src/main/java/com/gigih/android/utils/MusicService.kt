package com.gigih.android.utils

import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager

class MusicService : Service() {

    private var ringtone: Ringtone? = null

    override fun onBind(intent: Intent?) = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE))
        ringtone?.play()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        ringtone?.stop()
        super.onDestroy()
    }
}