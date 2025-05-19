package com.android.deepbookkeeping.ui.home

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.databinding.FragmentHomeBinding
import com.android.deepbookkeeping.ui.bottomsheet.AddTransactionDialogFragment

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.addTransactionFab.setOnClickListener {
            val dialog = AddTransactionDialogFragment().apply {
                setOnTransactionAddedListener(object : AddTransactionDialogFragment.OnTransactionAddedListener {
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
//            dialog.show(supportFragmentManager, AddTransactionDialogFragment.TAG)
            dialog.show(parentFragmentManager, AddTransactionDialogFragment.TAG)
        }
        return binding.root
    }
}