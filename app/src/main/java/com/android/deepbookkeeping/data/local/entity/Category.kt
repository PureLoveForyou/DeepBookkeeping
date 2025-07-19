package com.android.deepbookkeeping.data.local.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "categories")
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String,
    var type: Int,
    var isDefault: Boolean = false,
    var iconResourceId: Int
) : Parcelable
