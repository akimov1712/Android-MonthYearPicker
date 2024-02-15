package ru.topbun.customviewyearandmonthpicker

import ru.topbun.customviewyearandmonthpicker.screen.adapter.PickEntity
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentMonth
import ru.topbun.customviewyearandmonthpicker.utils.getCurrentYear

object Const {

    const val START_TIMESTAMP_YEAR = 1970

    private val listNameMonth = listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    val monthList = mutableListOf<PickEntity>().apply {
        listNameMonth.forEachIndexed { index, month ->
            add(
                PickEntity(
                    id = index,
                    value = month,
                    isEnabled = month == getCurrentMonth()
                )
            )
        }
    }


    val yearList = mutableListOf<PickEntity>().apply {
        val currentYear = getCurrentYear()
        (START_TIMESTAMP_YEAR..currentYear).forEach {
            add(
                PickEntity(
                    id = it-START_TIMESTAMP_YEAR,
                    value = it.toString(),
                    isEnabled = it == currentYear
                )
            )
        }
    }

}