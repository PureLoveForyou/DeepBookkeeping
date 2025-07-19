package com.android.deepbookkeeping.data.repository

import com.android.deepbookkeeping.data.local.dao.AppDao
import com.android.deepbookkeeping.data.local.entity.Transaction

class AccountingRepository(private val appDao: AppDao) {
    suspend fun insert(transaction: Transaction) = appDao.insert(transaction)

    suspend fun update(transaction: Transaction) = appDao.update(transaction)

    suspend fun delete(transaction: Transaction) = appDao.delete(transaction)

    fun getAllTransactions() = appDao.getAllTransactions()

    fun getTotalAmount(type: Int, startTime: Long, endTime: Long) =
        appDao.getTotalAmount(type, startTime, endTime)

    fun getCategoriesByType(type: Int) = appDao.getCategoriesByType(type)

    fun getAllTransactionWithCategories() = appDao.getAllTransactionWithCategories()

    fun getTransactionWithCategoriesById(id: Int) =
        appDao.getTransactionWithCategoriesById(id)
}