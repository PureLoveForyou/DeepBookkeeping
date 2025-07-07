package com.android.deepbookkeeping.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.deepbookkeeping.data.local.entity.Transaction

@Dao
interface AppDao {
    // 交易操作
    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query(
        """
        SELECT * FROM transactions
        ORDER BY date DESC
    """
    )
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query(
        """
        SELECT SUM(amount) FROM transactions
        WHERE type = :type
        AND date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalAmount(
        type: Int,
        startDate: Long,
        endDate: Long
    ): LiveData<Double?>
}