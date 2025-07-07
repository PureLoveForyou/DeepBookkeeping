package com.android.deepbookkeeping.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.local.entity.User

@Dao
interface AppDao {
    // 用户表操作
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: String): User?

    // 交易操作
    @Insert
    suspend fun insertTransaction(transaction: Transaction): Long

    @Query(
        """
        SELECT * FROM transactions 
        WHERE userId = :userId 
        ORDER BY date DESC
    """
    )
    fun getTransactionByUser(userId: String): LiveData<List<Transaction>>

    @Query(
        """
        SELECT SUM(amount) FROM transactions
        WHERE userId = :userId AND type = :type
        AND date BETWEEN :startDate AND :endDate
    """
    )
    fun getTotalAmountByType(
        userId: String,
        type: Int,
        startDate: Long,
        endDate: Long
    ): LiveData<Double?>
}