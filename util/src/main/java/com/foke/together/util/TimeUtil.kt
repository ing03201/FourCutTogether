package com.foke.together.util

import java.text.SimpleDateFormat
import java.util.Date

object TimeUtil {
    fun getCurrentTime(): String {
        // TODO. Implement this function
        //  - output format example: "2024.09.27"
        val timeFormat = "yyyyMMddHHmmss"
        val time = Date(System.currentTimeMillis())
        val simpletimeFormat = SimpleDateFormat(timeFormat)
        val simpleTime: String = simpletimeFormat.format(time)
        return simpleTime
    }
    fun getCurrentDate(): String {
        // TODO. Implement this function
        //  - output format example: "2024.09.27"
        val dateFormat = "yyyy.MM.dd"
        val date = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat(dateFormat)
        val simpleDate: String = simpleDateFormat.format(date)
        return simpleDate
    }
}