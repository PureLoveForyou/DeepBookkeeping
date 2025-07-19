package com.android.deepbookkeeping.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "transactions")
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var amount: Double,
    var category: String,
    var description: String? = null,
    var date: Long, // 使用时间戳
    var type: Int, // 0=支出, 1=收入
    var createdAt: Long = System.currentTimeMillis(),
    var categoryResourceId: Int
) : Parcelable