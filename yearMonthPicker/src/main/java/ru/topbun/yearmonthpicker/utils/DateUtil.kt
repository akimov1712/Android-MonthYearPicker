package ru.topbun.yearmonthpicker.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

fun getCurrentYear() = Calendar.getInstance().get(Calendar.YEAR)

fun getCurrentMonth(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat = SimpleDateFormat("MMM", Locale.ENGLISH)
    return dateFormat.format(currentDate).uppercase()
}

fun getTimeMillisFromMonthAndYear(month: String, year: String): Long {
    val monthFormat = SimpleDateFormat("MMM", Locale.US)
    val calendar = Calendar.getInstance()
    calendar.time = monthFormat.parse(month)
    calendar.set(Calendar.YEAR, year.toInt())
    return calendar.timeInMillis
}

fun formatTimeMillis(time: Long):String{
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = time
    val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    return "${dateFormat.format(calendar.time)}"
}