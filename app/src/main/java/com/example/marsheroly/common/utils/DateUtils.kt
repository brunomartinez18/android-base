package com.example.marsheroly.common.utils

import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.MutableDateTime

object DateUtils {

    fun epoch(): MutableDateTime {
        val epoch = MutableDateTime()
        epoch.millis = 0
        epoch.setDate(0)
        epoch.minuteOfDay = 0
        epoch.hourOfDay = 0
        epoch.secondOfDay = 0
        epoch.millisOfDay = 0
        return epoch
    }

    fun daysSinceEpoch(dateTime: DateTime): Int {
        val epoch = epoch()
        return daysBetween(epoch.toDateTime(), dateTime)
    }

    fun fromDaysSinceEpoch(daysBetweenEpoch: Int): DateTime {
        val dateTime = epoch()
        dateTime.addDays(daysBetweenEpoch)
        return dateTime.toDateTime()
    }

    fun daysBetween(startDate: DateTime, endDate: DateTime): Int {
        return Days.daysBetween(
            startDate.withTimeAtStartOfDay().toLocalDate(),
            endDate.withTimeAtStartOfDay().toLocalDate()
        ).days
    }

}