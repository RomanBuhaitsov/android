package com.example.shoppinglist

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class CartController(private val cartDb: Cart, val activity: MainActivity) {
    private var notificationId : Int = 0
    var builder = NotificationCompat.Builder(activity, CHANNEL_ID)
        .setContentTitle("textTitle")
        .setContentText("textContent")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    suspend fun writeItem(name: String,
                          price: String,){
        if(cartDb.writeItem(name, price))
            notify(name)
    }

    private fun notify(name: String) {
        with(NotificationManagerCompat.from(activity)) {
            notify(notificationId, builder.build())
            notificationId++
        }
    }
}