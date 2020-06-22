package com.example.nagyhazi.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.nagyhazi.R
import com.google.firebase.auth.FirebaseAuth

class LogoutService: Service() {
    private val CHANNEL_ID = "exampleServiceChannel"

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Logout Service")
            .setContentText("Observing recent list")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
        startForeground(1, notification);
        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        FirebaseAuth.getInstance().signOut()
        this.stopSelf()
    }
}