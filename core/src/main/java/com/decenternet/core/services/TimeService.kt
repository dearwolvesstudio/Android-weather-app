package com.decenternet.core.services

import com.decenternet.core.R
import com.decenternet.core.interfaces.IStringService
import com.decenternet.core.interfaces.ITimeService
import java.text.SimpleDateFormat
import java.util.*

class TimeService(private val _localiser: IStringService) : ITimeService {

    override fun getRelativeTimeSpan(date: Date): String {
        val deviceLocale = Locale.getDefault()

        val now = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time

        val differenceMillisFromNow = Math.abs(now.time - date.time)
        val diffDays = differenceMillisFromNow / (24 * 60 * 60 * 1000)

        when (diffDays.toInt()) {
            0 // Today
            -> {
                val minutesFromNow = differenceMillisFromNow / (60 * 1000) % 60
                val hoursFromNow = differenceMillisFromNow / (60 * 60 * 1000) % 24

                // More than an hour
                return if (hoursFromNow > 0) {

                    if (hoursFromNow > 1) {
                        String.format(_localiser.get(R.string.hours_ago), hoursFromNow)
                    } else {
                        String.format(_localiser.get(R.string.hour_ago), hoursFromNow)
                    }
                    // Less than an hour but more than 1 minute
                } else if (minutesFromNow > 0) {
                    if (minutesFromNow > 1) {
                        String.format(_localiser.get(R.string.mins_ago), minutesFromNow)
                    } else {
                        String.format(_localiser.get(R.string.min_ago), minutesFromNow)
                    }

                    // Less than 1 minute
                } else {
                    _localiser.get(R.string.moments_ago)
                }
            }
            1 // Yesterday
            -> {
                val timeHourMinuteDateFormatter = SimpleDateFormat("HH:mm", deviceLocale)
                return String.format(
                    _localiser.get(R.string.yesterday),
                    timeHourMinuteDateFormatter.format(date)
                )
            }
            2, 3, 4, 5, 6 // This week
            -> {
                val weekFormatter = SimpleDateFormat("EEEE, HH:mm", deviceLocale)
                return weekFormatter.format(date)
            }
            else -> {
                // More than 1 week
                val fullTimestampDateFormatter = SimpleDateFormat("MMM d, h:mm a", deviceLocale)
                return fullTimestampDateFormatter.format(date)
            }
        }
    }

    override fun dateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("MMM d, h:mm a")
        return dateFormat.format(date)
    }
}
