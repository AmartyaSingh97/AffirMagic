package com.example.affirmagic.utils

import java.time.LocalDate

object DateUtils {

    fun getPreviousDay(currentDate: String?, windowSize: Int = 7): String {
        if (currentDate == null) return LocalDate.now().minusDays((windowSize - 1).toLong()).toString()
        val localDate = LocalDate.parse(currentDate)
        return localDate.minusDays(1).toString()
    }

    fun getNextDay(currentDate: String?, windowSize: Int = 7): String {
        if (currentDate == null) return LocalDate.now().toString()
        val localDate = LocalDate.parse(currentDate)
        val nextDate = localDate.plusDays(1)
        return if (nextDate.isBefore(LocalDate.now().plusDays((windowSize - 1).toLong()))) {
            nextDate.toString()
        } else {
            currentDate
        }
    }

    fun isToday(date: String): Boolean {
        return LocalDate.parse(date) == LocalDate.now()
    }

    fun isSevenDaysBack(date: String): Boolean {
        return LocalDate.parse(date).isBefore(LocalDate.now().minusDays(6))
    }

    fun isAfterToday(date: String): Boolean {
        return LocalDate.parse(date).isAfter(LocalDate.now())
    }
}