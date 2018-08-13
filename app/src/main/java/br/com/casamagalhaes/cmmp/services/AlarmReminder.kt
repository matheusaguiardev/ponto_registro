package br.com.casamagalhaes.cmmp.services

import android.content.Context
import android.os.Vibrator


class AlarmReminder(val context: Context) {

    private fun vibrateSmartphone() {
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val pattern = longArrayOf(0, 100, 500, 100, 500, 100, 500, 100, 500, 100, 500)
        v.vibrate(pattern, -1)
    }

    fun alertVibrate() {
        vibrateSmartphone()
    }

    fun cancelVibrate(){
        val v = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.cancel()
    }

}