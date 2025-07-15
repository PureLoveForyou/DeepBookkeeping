package com.android.deepbookkeeping.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.ui.addTransaction.CategoryTabFragment

class CategoryViewPager2Adapter(
    fragment: Fragment,
    private val onItemSelected: (Category) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val transactionType =
            if (position == 0) Constants.TRANSACTION_EXPENSE else Constants.TRANSACTION_INCOME
        val fragment = CategoryTabFragment.newInstance(transactionType).apply {
            categorySelectedListener = object : CategoryTabFragment.OnCategorySelectedListener {
                override fun onCategorySelected(category: Category) {
                    onItemSelected(category)
                }
            }
        }
        return fragment
    }
}