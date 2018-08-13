package br.com.casamagalhaes.cmmp.controllers

import android.content.Context
import br.com.casamagalhaes.cmmp.helpers.MonthlyHelper
import br.com.casamagalhaes.cmmp.models.Daily
import br.com.casamagalhaes.cmmp.models.Monthly
import br.com.casamagalhaes.cmmp.utils.calendar.DateHelper
import java.util.*

class MonthlyController(context: Context) {

    private val monthlyHelper = MonthlyHelper(context = context)

    private fun saveMonthOfCurrentYear(month: Int) {
        monthlyHelper.save(month = createMonthOfCurrentYear(month = month))
    }

    fun getMonthByKey(key: String, context: Context): Monthly {
        val monthlyHelper = MonthlyHelper(context)
        val dateHelper = DateHelper()
        val monthly = monthlyHelper.getResumeByKey(key)
        if (monthly.resume?.isEmpty() == true) {
            val monthIndex = dateHelper.getMonthOfDateString(monthly.key!!)
            saveMonthOfCurrentYear(month = monthIndex)
            return createMonthOfCurrentYear(month = monthIndex)
        }
        return monthly
    }

    fun pointRecordByKey(now: Calendar, list: List<Daily>): Boolean{
        val dateHelper = DateHelper()
        val dateString = dateHelper.dateStringPattern(now)
        return monthlyHelper.pointRecord(monthKey = dateHelper.dateStringPatternForMonthKey(now), dailyKey = dateString, resumeMonth = list)
    }

    private fun createMonthOfCurrentYear(month: Int): Monthly {
        val listDays = DateHelper().generateDaysOfMonth(monthIndex = month)
        return setDaysOfMonth(listDays)
    }

    private fun setDaysOfMonth(keys: List<Calendar>): Monthly {
        val dateHelper = DateHelper()
        val dailies: MutableList<Daily> = mutableListOf()
        for (key in keys) {
            dailies.add(
                    Daily(
                            key = dateHelper.dateStringPattern(key),
                            checkIn = null,
                            lunchLeaving = null,
                            lunchReturn = null,
                            checkOut = null
                    )
            )
        }
        return Monthly(dateHelper.dateStringPatternForMonthKey(keys.first()), dailies)
    }

}