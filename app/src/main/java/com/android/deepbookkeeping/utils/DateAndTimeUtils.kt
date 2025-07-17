package com.android.deepbookkeeping.utils

import android.util.Log
import com.android.deepbookkeeping.data.constants.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateAndTimeUtils {
    const val TAG = Constants.TAG_PREFIX + "DateAndTimeUtils"

    @JvmStatic
    fun formatDate(timestamp: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return simpleDateFormat.format(Date(timestamp))
    }

    @JvmStatic
    fun formatTime(hour: Int, minute: Int): String {
        return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
    }

    @JvmStatic
    fun getTimeStamp(formattedDate: String, formattedTime: String): Long? {
        val dateTimeStr = "$formattedDate $formattedTime"
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return try {
            format.parse(dateTimeStr)?.time
        } catch (e: Exception) {
            Log.e(TAG, "Parse date and time failed: ${e.message}", e)
            null
        }
    }
}