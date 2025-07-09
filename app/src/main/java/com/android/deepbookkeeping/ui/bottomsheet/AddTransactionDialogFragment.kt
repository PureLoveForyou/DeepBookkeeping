package com.android.deepbookkeeping.ui.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.databinding.BottomSheetDialogAddTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textfield.TextInputEditText

class AddTransactionDialogFragment : BottomSheetDialogFragment() {

    // 控件声明
    private lateinit var etAmount: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var radioIncome: MaterialRadioButton
    private lateinit var radioExpense: MaterialRadioButton
    private lateinit var radioGroupType: RadioGroup
    private lateinit var chipGroupCategory: ChipGroup
    private lateinit var chipFood: Chip
    private lateinit var chipShopping: Chip
    private lateinit var chipTransport: Chip
    private lateinit var chipSalary: Chip
    private lateinit var chipOther: Chip
    private lateinit var btnSave: MaterialButton

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
        val binding = BottomSheetDialogAddTransactionBinding.inflate(inflater, container, false)
        // 初始化所有控件
        etAmount = binding.etAmount
        etDescription = binding.etDescription
        radioIncome = binding.radioIncome
        radioExpense = binding.radioExpense
        radioGroupType = binding.radioGroupType
        chipGroupCategory = binding.chipGroupCategory
        chipFood = binding.chipFood
        chipShopping = binding.chipShopping
        chipTransport = binding.chipTransport
        chipSalary = binding.chipSalary
        chipOther = binding.chipOther
        btnSave = binding.btnSave

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        // 默认选中支出
        radioExpense.isChecked = true

        btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val amountText = etAmount.text.toString()
        if (amountText.isEmpty()) {
            etAmount.error = "请输入金额"
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            etAmount.error = "请输入有效的金额"
            return
        }

        val description = etDescription.text.toString().trim()
        if (description.isEmpty()) {
            etDescription.error = "请输入描述"
            return
        }

        val type = when (radioGroupType.checkedRadioButtonId) {
            R.id.radioIncome -> 0
            R.id.radioExpense -> 1
            else -> 1
        }

        val selectedChipId = chipGroupCategory.checkedChipId
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