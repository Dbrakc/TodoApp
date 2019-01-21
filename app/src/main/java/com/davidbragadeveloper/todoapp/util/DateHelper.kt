package com.davidbragadeveloper.todoapp.util

import org.joda.time.DateTime
import org.joda.time.Period
import org.joda.time.PeriodType
import org.joda.time.format.ISOPeriodFormat
import org.joda.time.format.PeriodFormat
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

object DateHelper {

    val prettyTime by lazy { PrettyTime() }

    fun calculateTimaAgo (date: Date) : String = prettyTime.format(date)

}