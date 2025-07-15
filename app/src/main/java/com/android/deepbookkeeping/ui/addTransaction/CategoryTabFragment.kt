package com.android.deepbookkeeping.ui.addTransaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.android.deepbookkeeping.R
import com.android.deepbookkeeping.adapter.CategoryAdapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.databinding.FragmentCategoryTabBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryTabFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryType: Int? = null
    private lateinit var binding: FragmentCategoryTabBinding

    interface OnCategorySelectedListener {
        fun onCategorySelected(category: Category)
    }

    var categorySelectedListener: OnCategorySelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryType = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryTabBinding.inflate(inflater, container, false)
        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        val categoryAdapter = CategoryAdapter() { category ->
            categorySelectedListener?.onCategorySelected(category)
            Log.d(TAG, "选中类别: $category")
        }
        binding.categoryRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
            setHasFixedSize(true)
        }
        categoryAdapter.submitList(getCategoryList())
    }

    private fun getCategoryList(): List<Category> {
        if (categoryType == Constants.TRANSACTION_EXPENSE) {
            return arrayListOf(
                Category(1, "早餐", R.drawable.ic_food, Constants.TRANSACTION_EXPENSE),
                Category(2, "午餐", R.drawable.ic_food, Constants.TRANSACTION_EXPENSE),
                Category(3, "晚餐", R.drawable.ic_food, Constants.TRANSACTION_EXPENSE),
                Category(4, "交通", R.drawable.ic_transport, Constants.TRANSACTION_EXPENSE),
                Category(5, "购物", R.drawable.ic_shopping, Constants.TRANSACTION_EXPENSE),
                Category(6, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
                Category(7, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
                Category(8, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
                Category(9, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
                Category(10, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
                Category(11, "其他", R.drawable.ic_other, Constants.TRANSACTION_EXPENSE),
            )
        } else {
            return arrayListOf(
                Category(-1, "工资", R.drawable.ic_salary, Constants.TRANSACTION_INCOME),
                Category(-2, "津贴", R.drawable.ic_salary, Constants.TRANSACTION_INCOME),
                Category(-3, "奖金", R.drawable.ic_salary, Constants.TRANSACTION_INCOME),
                Category(-4, "红包", R.drawable.ic_salary, Constants.TRANSACTION_INCOME),
                Category(-5, "转账", R.drawable.ic_salary, Constants.TRANSACTION_INCOME),
                Category(-6, "其他", R.drawable.ic_other, Constants.TRANSACTION_INCOME),
            )
        }
    }

    companion object {
        const val TAG = Constants.TAG_PREFIX + "CategoryTabFragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment CategoryTabFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            CategoryTabFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}