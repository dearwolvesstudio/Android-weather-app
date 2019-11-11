package com.decenternet.core.interfaces

import java.util.Date

interface ITimeService {
    fun getRelativeTimeSpan(date: Date): String
    fun dateToString(date: Date): String
}
