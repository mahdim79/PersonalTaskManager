package com.task.taskmanager.utils

import java.util.Calendar

object Utils {

    fun convertCalendarTimeToShowFormat(calendar: Calendar):String{
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "$hour:$minute"
    }
}