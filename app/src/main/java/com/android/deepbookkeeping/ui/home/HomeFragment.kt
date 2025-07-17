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
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.databinding.FragmentHomeBinding
import com.android.deepbookkeeping.ui.addTransaction.AddTransactionFragment
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
            add(R.id.fullscreen_container, AddTransactionFragment.newInstance().apply {
                setOnTransactionAddedListener(object :
                    AddTransactionFragment.OnTransactionAddedListener {
                    override fun onTransactionAdded(
                        amount: Double,
                        timestamp: Long,
                        description: String,
                        category: Category
                    ) {
                        viewModel.insertTransaction(
                            Transaction(
                                amount = amount,
                                category = category.name,
                                type = category.type,
                                description = description,
                                date = timestamp,
                                categoryResourceId = category.iconResourceId
                            )
                        )
                    }
                })
            })
        }
    }

    private fun initRecyclerView() {
        binding.transactionsRecyclerView.apply {
            adapter = TransactionAdapter()
            setHasFixedSize(true)
        }
    }

    private fun initObserver() {
        viewModel.getAllTransactions().observe(viewLifecycleOwner, { transactions ->
            Log.d(TAG, "transactions' size = ${transactions?.size}")
            (binding.transactionsRecyclerView.adapter as? TransactionAdapter)?.submitList(
                transactions
            )
        })
    }
}