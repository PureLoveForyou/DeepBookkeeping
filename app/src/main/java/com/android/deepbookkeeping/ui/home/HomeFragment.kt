package com.android.deepbookkeeping.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.adapter.TransactionAdapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.relation.TransactionWithCategory
import com.android.deepbookkeeping.databinding.FragmentHomeBinding
import com.android.deepbookkeeping.ui.addTransaction.AddTransactionFragment
import com.android.deepbookkeeping.ui.transactionDetail.TransactionDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    companion object {
        const val TAG = Constants.TAG_PREFIX + "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.addTransactionFab.setOnClickListener {
            showAddTransactionFragment()
        }
        initObserver()
        initRecyclerView()
        return binding.root
    }

    private fun showAddTransactionFragment() {
        requireActivity().supportFragmentManager.commit {
            addToBackStack(AddTransactionFragment.TAG)
            add(R.id.fullscreen_container, AddTransactionFragment.newInstance(null))
        }
    }

    private fun initRecyclerView() {
        val transactionAdapter = TransactionAdapter { transaction ->
            Log.d(TAG, "on transaction click: $transaction")
            showEditTransactionFragment(transaction)
        }
        binding.transactionsRecyclerView.apply {
            adapter = transactionAdapter
        }
    }

    private fun showEditTransactionFragment(transaction: TransactionWithCategory) {
        requireActivity().supportFragmentManager.commit {
            addToBackStack(TransactionDetailFragment.TAG)
                .add(R.id.fullscreen_container, TransactionDetailFragment.newInstance(transaction))
        }
    }

    private fun initObserver() {
        viewModel.getAllTransactionWithCategories().observe(viewLifecycleOwner) { transactions ->
            Log.d(TAG, "transactions' size = ${transactions.size}")
            (binding.transactionsRecyclerView.adapter as? TransactionAdapter)?.submitList(
                transactions
            )
        }
        viewModel.getTodayIncome().observe(viewLifecycleOwner) { income ->
            binding.todayIncomeTextView.text = formatAmount(income ?: 0.0)
        }
        viewModel.getTodayExpense().observe(viewLifecycleOwner) { expense ->
            binding.todayExpenseTextView.text = formatAmount(expense ?: 0.0)
        }
        // 观察本月收入
        viewModel.getMonthIncome().observe(viewLifecycleOwner) { income ->
            binding.monthIncomeTextView.text = formatAmount(income ?: 0.0)
        }
        // 观察本月支出
        viewModel.getMonthExpense().observe(viewLifecycleOwner) { expense ->
            binding.monthExpenseTextView.text = formatAmount(expense ?: 0.0)
        }
    }

    // 格式化金额显示（添加¥符号和两位小数）
    private fun formatAmount(amount: Double): String {
        return "¥%.2f".format(amount)
    }
}