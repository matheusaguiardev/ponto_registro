package br.com.casamagalhaes.cmmp.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import br.com.casamagalhaes.cmmp.views.MainScrollingActivity


class NotificationFactory(val context: Context) {

    fun createNotification(msg: String) {

        val mBuilder = NotificationCompat.Builder(context)

        val intent = Intent(context, MainScrollingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        mBuilder.setContentIntent(pendingIntent)

        mBuilder.addAction(android.R.drawable.ic_menu_myplaces, "Marcar ponto agora!", pendingIntent)

        mBuilder.setSmallIcon(android.R.drawable.ic_menu_myplaces)
        mBuilder.setContentTitle("HORA DE BATER O PONTO !!")
        mBuilder.setContentText(msg)
        mBuilder.priority = NotificationCompat.PRIORITY_MAX


        val mNotificationManager =

                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(1, mBuilder.build())
    }

}