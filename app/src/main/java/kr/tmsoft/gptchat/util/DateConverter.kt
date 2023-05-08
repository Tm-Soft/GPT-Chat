package kr.tmsoft.gptchat.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {
    fun getFullDate(): Long =
        SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault())
        .format(
            Date(System.currentTimeMillis())
        ).toLong()
}