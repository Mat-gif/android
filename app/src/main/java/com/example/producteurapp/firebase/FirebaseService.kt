package com.example.producteurapp.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.producteurapp.R

class FirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d("FIREBASE_BAM", message.data.toString())
        // Récupérer les données du message
        val data = message.data
        val body = "Super ta commande t'attend !"
        val handler = Handler(Looper.getMainLooper())

        handler.post{
            Toast.makeText(this, "Nouvelle commande en attente!", Toast.LENGTH_LONG).show()
        }

        val notificationId = 1
        val channelId = "app_commerce"
        val channelName = "app_commercer_pro"
        createNotificationChannel(channelId, channelName)

        // Construire la notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_logo_application) // Icône de notification
            .setContentTitle("Nouvelle commande en attente!") // Titre de la notification
            .setContentText(body) // Texte de la notification
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priorité de la notification
            .setAutoCancel(true)
        // Afficher la notification à l'aide du NotificationManager
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
    
    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
