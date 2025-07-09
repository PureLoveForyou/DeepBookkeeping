package com.android.deepbookkeeping.ui.statistics

import androidx.lifecycle.ViewModel
import com.android.deepbookkeeping.data.repository.AccountingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: AccountingRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}