package com.task.taskmanager.utils

import java.util.Calendar
import kotlin.math.min

object Utils {

    fun convertCalendarTimeToShowFormat(calendar: Calendar):String{
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return "${String.format("%02d",hour)}:${String.format("%02d", minute)}"
    }
}