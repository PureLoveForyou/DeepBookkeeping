package com.android.deepbookkeeping.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String, // 关联的用户ID
    val amount: Double,
    val category: String,
    val description: String? = null,
    val date: Long, // 使用时间戳
    val type: Int, // 0=支出, 1=收入
    val createdAt: Long = System.currentTimeMillis()
)