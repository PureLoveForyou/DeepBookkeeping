package com.android.deepbookkeeping.ui.transactionDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor(private val repository: AccountingRepository) :
    ViewModel() {
    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.delete(transaction)
        }
    }
}