package com.gery.mobile.common.extensions

import com.example.marsheroly.R
import com.example.marsheroly.common.Constants
import com.example.marsheroly.presentation.MarsHerolyApplication
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil
import org.joda.time.DateTime

/**
 * Method to convert a Date from local timezone to central timezone (GMT)
 */
fun Date.toCentralTime(): Date {
    // Get local calendar instance
    val localCalendar = Calendar.getInstance()
    localCalendar.time = this
    val localHour = localCalendar.get(Calendar.HOUR_OF_DAY)

    // Get GMT calendar instance
    val gmtCalendar = GregorianCalendar(TimeZone.getTimeZone("GMT"))
    gmtCalendar.time = this
    val gmtHour = gmtCalendar.get(Calendar.HOUR_OF_DAY)

    // Get the hour difference and add it to local time
    val hourDifference = gmtHour - localHour
    localCalendar.add(Calendar.HOUR_OF_DAY, hourDifference)

    return localCalendar.time
}

/**
 * Method to convert a Date from central timezone to local timezone
 */
fun Date.fromCentralTime(): Date {
    // Get central date as string
    val dateAsString = this.format(Constants.COMPLETE_FORMAT)

    // Instance formatter with GTM time zone
    val dateFormat = SimpleDateFormat(Constants.COMPLETE_FORMAT, Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("GMT")

    return dateFormat.parse(dateAsString)
}

/**
 * Method to check if the date is from the past
 */
fun Date.isFromPast(includeToday: Boolean = false): Boolean {
    val today = Calendar.getInstance().time
    return if (includeToday) {
        this <= today
    } else {
        this < today
    }
}

/**
 * Method to check if the date is from the future
 */
fun Date.isFromFuture(includeToday: Boolean = false): Boolean {
    val today = Calendar.getInstance().time
    return if (includeToday) {
        this >= today
    } else {
        this > today
    }
}

/**
 * Format a date to a passed pattern
 * @param pattern:
 * "YYYY-MM-dd" - date
 * "EEE, d MMM" - date w/week day
 * "YYYY-MM-dd HH:mm" - dateTime
 * "EEE, d MMM HH:mm" - dateTime w/week day
 */
fun Date.format(pattern: String): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return when (pattern) {
        "EEE, d MMM HH:mm" -> {
            if (isToday()) {
                val today = MarsHerolyApplication.get().applicationContext.getString(R.string.today)
                val time = this.format("HH:mm")
                "$today $time"
            } else {
                dateFormat.format(this)
            }
        }
        "EEE, d MMM" -> {
            if (isToday()) {
                MarsHerolyApplication.get().applicationContext.getString(R.string.today)
            } else {
                dateFormat.format(this)
            }
        }
        else -> dateFormat.format(this)
    }
}

/**
 * Returns if this day is today's day
 */
fun Date.isToday(): Boolean {
    val today = Calendar.getInstance()
    val todayDate = today.time
    return this.isSameDay(todayDate)
}

/**
 * Compares whether this date and a given date are the same
 */
fun Date.isSameDay(compareTo: Date): Boolean {
    val thisDate = Calendar.getInstance()
    thisDate.time = this
    val compareToDate = Calendar.getInstance()
    compareToDate.time = compareTo
    val sameYear = thisDate.get(Calendar.YEAR) == compareToDate.get(Calendar.YEAR)
    val sameMonth = thisDate.get(Calendar.MONTH) == compareToDate.get(Calendar.MONTH)
    val sameDay = thisDate.get(Calendar.DAY_OF_MONTH) == compareToDate.get(Calendar.DAY_OF_MONTH)
    return sameYear && sameMonth && sameDay
}

/**
 * Method to get a Date from this with the distance specified in the params.
 * Positive ints add, negatives subtract
 * @param difDay
 * @param difMonth
 * @param difYear
 * @return Date with the specified difference
 */
fun Date.getDateWithDiff(difDay: Int, difMonth: Int, difYear: Int): Date {
    val calThisDate = Calendar.getInstance()
    calThisDate.time = this
    calThisDate.add(Calendar.DAY_OF_MONTH, difDay)
    calThisDate.add(Calendar.MONTH, difMonth)
    calThisDate.add(Calendar.YEAR, difYear)
    return calThisDate.time
}

/**
 * Method to format a date to String according to today.
 * If SAME MONTH && YEAR -> only show the day
 * If DIF MONTH, SAME YEAR -> show dd/MM
 * If DIF MONTH && YEAR -> show dd/MM/YYYY
 */
fun Date.contextualFormat(): String {
    val today = Calendar.getInstance()
    val calThisDate = Calendar.getInstance()
    calThisDate.time = this
    val day = calThisDate.get(Calendar.DAY_OF_MONTH)
    val month = calThisDate.get(Calendar.MONTH)
    val year = calThisDate.get(Calendar.YEAR)
    // Remember! months in Calendar go from 0 to 11 (add 1)
    return if (today.get(Calendar.YEAR) != year) "${day}/${month + 1}/${year}"
    else if (today.get(Calendar.MONTH) != month) "${day}/${month + 1}"
    else day.toString()
}

/**
 * Method to format a date to String according to today.
 * If TODAY -> Today
 * If DIF MONTH, SAME YEAR -> show dd/MM
 * If DIF MONTH && YEAR -> show dd/MM/YYYY
 */
fun Date.formatWithToday(): String {
    if (this.isToday()) return MarsHerolyApplication.get().applicationContext.getString(R.string.today)
    val today = Calendar.getInstance()
    val calThisDate = Calendar.getInstance()
    calThisDate.time = this
    val day = calThisDate.get(Calendar.DAY_OF_MONTH)
    val month = calThisDate.get(Calendar.MONTH)
    val year = calThisDate.get(Calendar.YEAR)
    // Remember! months in Calendar go from 0 to 11 (add 1)
    return if (today.get(Calendar.YEAR) != year) "${day}/${month + 1}/${year}"
    else "${day}/${month + 1}"
}

fun Date.toLocalDate(): LocalDate {
    val calThisDate = Calendar.getInstance()
    calThisDate.time = this
    val day = calThisDate.get(Calendar.DAY_OF_MONTH)
    val month = calThisDate.get(Calendar.MONTH) + 1
    val year = calThisDate.get(Calendar.YEAR)
    return LocalDate.of(year, month, day)
}

fun List<Date>.toLocalDateList(): MutableList<LocalDate> {
    val auxList = mutableListOf<LocalDate>()
    this.forEach { date ->
        auxList.add(date.toLocalDate())
    }
    return auxList
}

fun LocalDate.toDate(): Date {
    val day = this.dayOfMonth
    val month = this.monthValue - 1
    val year = this.year
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    return calendar.time
}

fun List<LocalDate>.toDateList(): MutableList<Date> {
    val auxList = mutableListOf<Date>()
    this.forEach { date ->
        auxList.add(date.toDate())
    }
    return auxList
}

fun Date.stringHourDifferenceWith(other: Date): String {
    val diff = this.time - other.time
    val diffInMinutes = ceil(diff / (1000.toDouble() * 60)).toInt()

    val hourDifference = diffInMinutes / 60
    val minuteDifference = diffInMinutes.rem(60)
    val minuteDifferenceString = if (minuteDifference < 10) "0$minuteDifference" else minuteDifference.toString()

    return "$hourDifference:$minuteDifferenceString"
}

fun DateTime.withFirstDayOfWeek(): DateTime = minusDays(dayOfWeek - 1)

fun DateTime.withFirstDayOfMonth(): DateTime = minusDays(dayOfMonth - 1)

fun DateTime.withFirstDayOfYear(): DateTime = minusDays(dayOfYear - 1)