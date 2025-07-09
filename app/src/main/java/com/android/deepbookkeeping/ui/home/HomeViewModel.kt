package com.android.deepbookkeeping.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AccountingRepository
) : ViewModel() {
    fun getAllTransactions(): LiveData<List<Transaction>> = repository.getAllTransactions()
    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insert(transaction)
        }
    }
}