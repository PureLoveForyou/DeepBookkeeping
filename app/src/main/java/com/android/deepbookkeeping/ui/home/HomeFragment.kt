package com.android.deepbookkeeping.ui.home

import android.nfc.Tag
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.databinding.FragmentHomeBinding
import com.android.deepbookkeeping.ui.bottomsheet.AddTransactionDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        const val TAG = Constants.TAG_PREFIX + "HomeFragment"
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.addTransactionFab.setOnClickListener {
            val dialog = AddTransactionDialogFragment().apply {
                setOnTransactionAddedListener(object :
                    AddTransactionDialogFragment.OnTransactionAddedListener {
                    override fun onTransactionAdded(
                        amount: Double,
                        description: String,
                        type: String,
                        category: String
                    ) {
                        // 处理保存的交易数据
//                        saveTransactionToDatabase(amount, description, type, category)
                        // 刷新UI
//                        refreshTransactionList()
                    }
                })
            }
            dialog.show(parentFragmentManager, AddTransactionDialogFragment.TAG)
        }
        initObserver()
        return binding.root
    }

    private fun initObserver() {
        viewModel.getAllTransactions().observe(viewLifecycleOwner, { transactions ->
            Log.d(TAG, "transactions' size = ${transactions?.size}")
        })
    }
}