package com.android.deepbookkeeping.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String, // 可以使用Firebase UID或UUID生成
    val name: String,
    val email: String,
    val createdAt: Long = System.currentTimeMillis()
)