package com.android.deepbookkeeping.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var amount: Double,
    var category: String,
    var description: String? = null,
    var date: Long, // 使用时间戳
    var type: Int, // 0=支出, 1=收入
    var createdAt: Long = System.currentTimeMillis()
)