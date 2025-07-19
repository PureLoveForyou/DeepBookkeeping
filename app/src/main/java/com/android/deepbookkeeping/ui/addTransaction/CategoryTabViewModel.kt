package com.android.deepbookkeeping.ui.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryTabViewModel @Inject constructor(private val repository: AccountingRepository) :
    ViewModel() {
    fun getCategoriesByType(type: Int): LiveData<List<Category>> =
        repository.getCategoriesByType(type)

}