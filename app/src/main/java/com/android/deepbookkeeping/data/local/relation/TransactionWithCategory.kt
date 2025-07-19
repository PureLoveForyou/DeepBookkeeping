package com.android.deepbookkeeping.data.local.relation

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.local.entity.Transaction
import kotlinx.parcelize.Parcelize

@Parcelize
data class TransactionWithCategory(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: Category
) : Parcelable
