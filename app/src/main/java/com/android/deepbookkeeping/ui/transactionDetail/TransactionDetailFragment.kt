package com.android.deepbookkeeping.ui.transactionDetail

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.relation.TransactionWithCategory
import com.android.deepbookkeeping.databinding.FragmentTransactionDetailBinding
import com.android.deepbookkeeping.ui.addTransaction.AddTransactionFragment
import com.android.deepbookkeeping.utils.DateAndTimeUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [TransactionDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var selectedTransaction: TransactionWithCategory? = null
    private lateinit var binding: FragmentTransactionDetailBinding
    private val viewmodel: TransactionDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            selectedTransaction = it.getParcelable(ARG_PARAM1, TransactionWithCategory::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        // Inflate the layout for this fragment
        binding = FragmentTransactionDetailBinding.inflate(inflater, container, false)
        initView()
        initClickListener()
        return binding.root
    }

    private fun initView() {
        selectedTransaction?.let { item ->
            updateView(item)
            viewmodel.getTransactionWithCategory(item.transaction.id).observe(viewLifecycleOwner) {
                updateView(it)
            }
        }
    }

    private fun updateView(item: TransactionWithCategory) {
        binding.transactionIcon.setImageResource(item.category.iconResourceId)
        val amountText =
            if (item.transaction.type == Constants.TRANSACTION_EXPENSE) "- " + item.transaction.amount else item.transaction.amount.toString()
        binding.transactionAmount.text = amountText
        binding.categoryText.text = item.category.name
        binding.transactionTime.text =
            DateAndTimeUtils.formatDateAndTime(item.transaction.date)
        binding.transactionRemark.text = item.transaction.description
        binding.transactionType.text =
            if (item.transaction.type == Constants.TRANSACTION_EXPENSE)
                getString(R.string.category_expense)
            else getString(
                R.string.category_income
            )
    }

    private fun initClickListener() {
        binding.editTransactionToolbar.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.deleteTransactionButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("提示")
                .setMessage("确定要删除该条账单吗？删除后不可恢复")
                .setPositiveButton(R.string.confirm) { dialog, _ ->
                    deleteTransaction(dialog)
                }
                .setNegativeButton(R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(true)
                .show()
        }
        binding.editTransactionButton.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                addToBackStack(AddTransactionFragment.TAG)
                add(
                    R.id.fullscreen_container,
                    AddTransactionFragment.newInstance(selectedTransaction)
                )
            }
        }
    }

    private fun deleteTransaction(dialog: DialogInterface) {
        selectedTransaction?.let { item ->
            viewmodel.deleteTransaction(
                item.transaction
            )
        }
        dialog.dismiss()
        requireActivity().supportFragmentManager.popBackStack()
    }

    companion object {
        const val TAG = Constants.TAG_PREFIX + "TransactionDetailFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param transaction Parameter 1.
         * @return A new instance of fragment EditTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(transaction: TransactionWithCategory) =
            TransactionDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, transaction)
                }
            }
    }
}