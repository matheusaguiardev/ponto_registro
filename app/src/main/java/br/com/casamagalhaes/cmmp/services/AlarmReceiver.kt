package br.com.casamagalhaes.cmmp.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import br.com.casamagalhaes.cmmp.utils.Constants


class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(k1: Context, k2: Intent) {
        val msg = k2.getStringExtra(Constants.M_NOTIFICATION)
        NotificationFactory(k1).createNotification(msg)
        val alarm = AlarmReminder(k1)
        alarm.alertVibrate()
    }

}