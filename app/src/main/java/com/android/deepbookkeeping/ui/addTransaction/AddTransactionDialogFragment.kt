package com.android.deepbookkeeping.ui.addTransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.databinding.BottomSheetDialogAddTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTransactionDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetDialogAddTransactionBinding

    interface OnTransactionAddedListener {
        fun onTransactionAdded(
            amount: Double,
            description: String,
            type: Int,
            category: String
        )
    }

    private var listener: OnTransactionAddedListener? = null

    fun setOnTransactionAddedListener(listener: OnTransactionAddedListener) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 使用ViewBinding获取视图（但不用于数据绑定）
        binding = BottomSheetDialogAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        // 默认选中支出
        binding.radioExpense.isChecked = true

        binding.btnSave.setOnClickListener {
            saveTransaction()
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
        if (description.isEmpty()) {
            binding.etDescription.error = "请输入描述"
            return
        }

        val type = when (binding.radioGroupType.checkedRadioButtonId) {
            R.id.radioIncome -> 0
            R.id.radioExpense -> 1
            else -> 1
        }

        val selectedChipId = binding.chipGroupCategory.checkedChipId
        if (selectedChipId == View.NO_ID) {
            Toast.makeText(context, "请选择分类", Toast.LENGTH_SHORT).show()
            return
        }

        val category = when (selectedChipId) {
            R.id.chipFood -> "餐饮"
            R.id.chipShopping -> "购物"
            R.id.chipTransport -> "交通"
            R.id.chipSalary -> "工资"
            R.id.chipOther -> "其他"
            else -> "其他"
        }

        listener?.onTransactionAdded(amount, description, type, category)
        dismiss()
    }

    companion object {
        const val TAG = "AddTransactionDialogFragment"
    }
}