package br.com.casamagalhaes.cmmp.utils.calendar

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    private val formatBra = SimpleDateFormat("d/M/yyyy")

    fun dateStringPattern(calendar: Calendar): String = "${calendar.get(Calendar.DATE)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)}"

    fun dateStringPatternForMonthKey(key: Calendar): String = """${key.get(Calendar.MONTH)+1}/${key.get(Calendar.YEAR)}"""

    fun generateDaysOfMonth(monthIndex: Int): List<Calendar> {
        val calendarGreg = GregorianCalendar.getInstance()
        calendarGreg.set(Calendar.MONTH, monthIndex-1)
        val lastDay: Int = calendarGreg.getActualMaximum(Calendar.DAY_OF_MONTH)
        var count = 0
        val dayList = mutableListOf<GregorianCalendar>()

        val day = Calendar.getInstance()
        day.set(Calendar.DAY_OF_MONTH, 1)
        while (count < lastDay) {
            dayList.add(convertCalendarToGregorian(day))
            day.add(Calendar.DAY_OF_MONTH, 1)
            count++
        }
        return dayList
    }

    fun formatKeyDate(date: String?): String {
        return try {
            val newData = formatBra.parse(date.toString())
            formatBra.format(newData)
        } catch (Ex: ParseException) {
            "Erro na conversão da data"
        }
    }

    fun convertStringToDate(date: String?): Date {
        return try {
            formatBra.parse(date.toString())
        } catch (Ex: ParseException) {
            Calendar.getInstance().time
        }
    }

    fun convertDateToString(date: Date?): String {
        val today = date?.time
        return formatBra.format(today)
    }

    fun getHourByDate(date: Date?): String {
        if (date == null) return "--:--"

        val calendar = GregorianCalendar.getInstance()
        calendar.time = date
        calendar.get(Calendar.HOUR_OF_DAY)
        calendar.get(Calendar.HOUR)
        calendar.get(Calendar.MONTH)

        return """${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"""
    }

    fun isToday(key: String?): Boolean {
        val nowDate = convertDateToString(Calendar.getInstance().time)
        return nowDate == key
    }

    fun getMonthOfDateString(date: String): Int {
        val dateSplited = date.split("/")
        return dateSplited[0].toInt()
    }

    private fun convertCalendarToGregorian(day: Calendar): GregorianCalendar {
        val calendar = GregorianCalendar()
        calendar.time = day.time
        return calendar
    }


}