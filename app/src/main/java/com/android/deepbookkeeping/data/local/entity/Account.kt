package com.android.deepbookkeeping.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["userId"])]
)
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String, // 关联的用户ID
    val name: String,
    val balance: Double,
    val currency: String = "CNY",
    val iconRes: String = "ic_account_default",
    val isDefault: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)