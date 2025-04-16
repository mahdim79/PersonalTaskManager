package com.task.taskmanager.utils

import android.icu.util.Calendar

object Utils {

    fun validateTaskTime(timeInMillis:Long):Boolean = timeInMillis > System.currentTimeMillis()

    fun convertCalendarTimeToShowFormat(calendar: Calendar):String{
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$year/$month/$day - $hour:$minute"
    }
}