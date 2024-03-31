package com.balex.broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlin.concurrent.thread
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyService : Service() {

    private val localBroadcastManager by lazy {
        LocalBroadcastManager.getInstance(this)
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            for (i in 1..10) {
                Thread.sleep(1000)
                Intent("loaded").apply {
                    putExtra("percent", i * 10)
                    localBroadcastManager.sendBroadcast(this)
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}