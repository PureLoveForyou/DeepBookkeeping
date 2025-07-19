package com.android.deepbookkeeping.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.local.relation.TransactionWithCategory
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AccountingRepository
) : ViewModel() {
    fun getAllTransactions(): LiveData<List<Transaction>> = repository.getAllTransactions()
    fun getAllTransactionWithCategories(): LiveData<List<TransactionWithCategory>> = repository.getAllTransactionWithCategories()
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }

    fun getTodayIncome(): LiveData<Double?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + 24 * 60 * 60 * 1000 - 1
        return repository.getTotalAmount(Constants.TRANSACTION_INCOME, startOfDay, endOfDay)
    }

    fun getTodayExpense(): LiveData<Double?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfDay = calendar.timeInMillis
        val endOfDay = startOfDay + 24 * 60 * 60 * 1000 - 1
        return repository.getTotalAmount(Constants.TRANSACTION_EXPENSE, startOfDay, endOfDay)
    }

    fun getMonthIncome(): LiveData<Double?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfMonth = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        val endOfMonth = calendar.timeInMillis - 1
        return repository.getTotalAmount(Constants.TRANSACTION_INCOME, startOfMonth, endOfMonth)
    }

    fun getMonthExpense(): LiveData<Double?> {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val startOfMonth = calendar.timeInMillis

        calendar.add(Calendar.MONTH, 1)
        val endOfMonth = calendar.timeInMillis - 1
        return repository.getTotalAmount(Constants.TRANSACTION_EXPENSE, startOfMonth, endOfMonth)
    }
}