package com.balex.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "loaded") {
                val percent = intent.getIntExtra("percent", 0)
                progressBar.progress = percent
            } else if (intent?.action == MyReceiver.ACTION_CLICKED) {
                val count = intent.getIntExtra(MyReceiver.EXTRA_COUNT, 0)
                Toast.makeText(context, "Clicked $count", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //private val receiver = MyReceiver()

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        findViewById<Button>(R.id.button).setOnClickListener {
            Intent(MyReceiver.ACTION_CLICKED).apply {
                putExtra(MyReceiver.EXTRA_COUNT, count++)
                sendBroadcast(this)
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction("loaded")
            addAction(MyReceiver.ACTION_CLICKED)
        }
        registerReceiver(receiver, intentFilter, RECEIVER_EXPORTED)
        Intent(this, MyService::class.java).apply {
            startService(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}


