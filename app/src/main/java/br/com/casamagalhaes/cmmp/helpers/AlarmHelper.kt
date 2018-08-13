package br.com.casamagalhaes.cmmp.helpers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import br.com.casamagalhaes.cmmp.services.AlarmReceiver
import br.com.casamagalhaes.cmmp.utils.Constants
import java.util.*

class AlarmHelper(val context: Context) {

    companion object {
        const val RQS_1 = 1
    }

   fun createAlarm(date: Date, msg:String){
       val calendar = Calendar.getInstance();
       calendar.time = date
       setAlarm(calendar, msg)
   }

    fun predictionLunchReturn(date: Date): Date? {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Constants.AWAIT_UNIT_TIME, Constants.AWAIT_TIME)
        return cal.time
    }

    private fun setAlarm(targetCal: Calendar, msg:String) {
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(Constants.M_NOTIFICATION, msg)
        val pendingIntent = PendingIntent.getBroadcast(
                context, RQS_1, intent, 0)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.timeInMillis,
                pendingIntent)
    }

}