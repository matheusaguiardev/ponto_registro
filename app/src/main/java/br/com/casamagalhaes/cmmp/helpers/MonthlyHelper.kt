package br.com.casamagalhaes.cmmp.helpers

import android.content.Context
import br.com.casamagalhaes.cmmp.models.Daily
import br.com.casamagalhaes.cmmp.models.Monthly
import com.snappydb.DB
import com.snappydb.DBFactory
import java.util.*

class MonthlyHelper(private val context: Context) {

    fun save(month: Monthly) {

        val snappyDB: DB = DBFactory.open(context)
        try {
            snappyDB.put(month.key, month)
        } catch (e: Exception){
            print(e.message)
        } finally {
            snappyDB.close()
        }

    }

    fun getResumeByKey(key: String): Monthly {
        val snappyDB: DB = DBFactory.open(context)
        return try {
            val objDB = snappyDB.getObject(key, Monthly::class.java)
            Monthly(key, objDB.resume)
        } catch (e: Exception){
            Monthly(key, emptyList())
        } finally {
            snappyDB.close()
        }
    }

    fun pointRecord(monthKey: String, dailyKey: String, resumeMonth: List<Daily> ): Boolean {

        for(daily in resumeMonth){
            if(daily.key.equals(dailyKey)){
                when {
                    daily.checkIn == null -> daily.checkIn = getNow()
                    daily.lunchLeaving == null -> daily.lunchLeaving = getNow()
                    daily.lunchReturn == null -> daily.lunchReturn = getNow()
                    daily.checkOut == null -> daily.checkOut = getNow()
                    else -> {
                        return false
                    }
                }
                break
            }
        }

        save(Monthly(key = monthKey, resume = resumeMonth))
        return true
    }

    fun editPointRecord(dailyEdited: Daily, key: String){
        val monthList = getResumeByKey(key).resume
        if (monthList != null) {
            for(daily in monthList){
                if(daily.key.equals(dailyEdited.key)){
                    daily.checkIn = dailyEdited.checkIn
                    daily.checkOut = dailyEdited.checkOut
                    daily.lunchReturn = dailyEdited.lunchReturn
                    daily.lunchLeaving = dailyEdited.lunchLeaving
                    break
                }
            }
        }
        save(Monthly(key, monthList))
    }

    private fun getNow(): Date{
        return Calendar.getInstance().time
    }

}