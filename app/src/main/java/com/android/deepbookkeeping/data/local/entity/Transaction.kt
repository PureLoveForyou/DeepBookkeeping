package com.android.deepbookkeeping.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_DEFAULT
        )]
)
@Parcelize
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var amount: Double,
    var description: String? = null,
    var date: Long, // 使用时间戳
    var type: Int, // 0=支出, 1=收入
    var createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(index = true) var categoryId: Int = 0
) : Parcelable