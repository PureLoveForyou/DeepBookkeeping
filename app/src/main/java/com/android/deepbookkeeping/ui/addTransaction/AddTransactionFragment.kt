package com.android.deepbookkeeping.ui.addTransaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.transition.TransitionInflater
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.adapter.CategoryViewPager2Adapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.databinding.FragmentAddTransactionBinding
import com.android.deepbookkeeping.utils.DateAndTimeUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTransactionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var currentTransaction: Transaction? = null
    private lateinit var binding: FragmentAddTransactionBinding
    private var selectedCategory: Category? = null

    interface OnTransactionAddedListener {
        fun onTransactionAdded(
            amount: Double,
            timestamp: Long,
            description: String,
            category: Category
        )
    }

    private var listener: OnTransactionAddedListener? = null

    fun setOnTransactionAddedListener(listener: OnTransactionAddedListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentTransaction = it.getParcelable(ARG_PARAM1, Transaction::class.java)
        }
        enterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(R.transition.slide_bottom)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false)
        initViewPager2()
        initClickListener()
        initView()
        return binding.root
    }

    private fun initView() {
        val calendar = Calendar.getInstance()
        binding.editTransactionDate.text = DateAndTimeUtils.formatDate(calendar.timeInMillis)
        binding.editTransactionTime.text = DateAndTimeUtils.formatTime(
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )
        // 如果传进来的参数有transaction，则为编辑模式
        currentTransaction?.let {
            binding.etAmount.setText(it.amount.toString())
            binding.etDescription.setText(it.description)
            if (it.type == Constants.TRANSACTION_EXPENSE) {
                binding.categoryViewpager2.setCurrentItem(0, true)
            } else {
                binding.categoryViewpager2.setCurrentItem(1, true)
            }
            binding.editTransactionDate.text = DateAndTimeUtils.formatDate(it.date)
            binding.editTransactionTime.text = DateAndTimeUtils.formatTime(it.date)
        }
    }

    private fun initClickListener() {
        binding.cancelButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.saveButton.setOnClickListener {
            saveTransaction()
        }
        binding.editTransactionDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.choose_date))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.addOnPositiveButtonClickListener { selection ->
                binding.editTransactionDate.text = DateAndTimeUtils.formatDate(selection)
            }
            datePicker.show(childFragmentManager, "DATE_PICKER")
        }
        binding.editTransactionTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText(getString(R.string.choose_time))
                .build()
            timePicker.addOnPositiveButtonClickListener {
                binding.editTransactionTime.text =
                    DateAndTimeUtils.formatTime(timePicker.hour, timePicker.minute)
            }
            timePicker.show(childFragmentManager, "TIME_PICKER")
        }
    }

    private fun saveTransaction() {
        val amountText = binding.etAmount.text.toString()
        if (amountText.isEmpty()) {
            binding.etAmount.error = "请输入金额"
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            binding.etAmount.error = "请输入有效的金额"
            return
        }

        val description = binding.etDescription.text.toString().trim()
        val timestamp = DateAndTimeUtils.getTimeStamp(
            binding.editTransactionDate.text.toString().trim(),
            binding.editTransactionTime.text.toString().trim()
        ) ?: System.currentTimeMillis()
        selectedCategory?.let {
            listener?.onTransactionAdded(amount, timestamp, description, it)
            parentFragmentManager.popBackStack()
        } ?: Toast.makeText(requireContext(), "请选择一种类别", Toast.LENGTH_SHORT).show()
    }

    private fun initViewPager2() {
        val viewPager2Adapter = CategoryViewPager2Adapter(this) { category: Category ->
            selectedCategory = category
            Log.d(TAG, "选中类别: $category")
        }
        binding.categoryViewpager2.adapter = viewPager2Adapter
        TabLayoutMediator(binding.categoryTabLayout, binding.categoryViewpager2) { tab, position ->
            tab.text =
                if (position == 0) getString(R.string.category_expense) else getString(R.string.category_income)
        }.attach()
    }

    companion object {
        const val TAG = Constants.TAG_PREFIX + "AddTransactionFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param transaction Parameter 1.
         * @return A new instance of fragment AddTransactionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(transaction: Transaction?) =
            AddTransactionFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, transaction)
                }
            }
    }
}