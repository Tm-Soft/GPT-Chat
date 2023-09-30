package kr.tmsoft.gptchat.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {
    // 테스트 커밋.
    fun getFullDate(): Long =
        SimpleDateFormat("yyyyMMddkkmmss", Locale.getDefault())
        .format(
            Date(System.currentTimeMillis())
        ).toLong()
}