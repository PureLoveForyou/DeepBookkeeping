package com.android.deepbookkeeping.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.local.relation.TransactionWithCategory

@Dao
interface AppDao {
    // 交易操作
    @Insert
    suspend fun insert(transaction: Transaction): Long

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
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

    // 类别操作
    @Insert
    suspend fun insert(category: Category): Long

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): LiveData<List<Category>>

    @Query("SELECT * FROM categories WHERE type = :type")
    fun getCategoriesByType(type: Int): LiveData<List<Category>>

    // 联合操作
    @androidx.room.Transaction
    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactionWithCategories(): LiveData<List<TransactionWithCategory>>

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE id = :id ORDER BY date DESC")
    fun getTransactionWithCategoriesById(id: Int): LiveData<TransactionWithCategory>

    @androidx.room.Transaction
    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionByTypeWithCategories(type: Int): LiveData<List<TransactionWithCategory>>
}