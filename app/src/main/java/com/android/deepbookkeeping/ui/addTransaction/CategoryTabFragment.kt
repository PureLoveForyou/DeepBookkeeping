package com.android.deepbookkeeping.ui.addTransaction

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.android.deepbookkeeping.adapter.CategoryAdapter
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.databinding.FragmentCategoryTabBinding
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryTabFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CategoryTabFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var categoryType: Int? = null
    private lateinit var binding: FragmentCategoryTabBinding
    private var currentSelectedCategory: Category? = null
    private val viewmodel: CategoryTabViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

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
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryTabBinding.inflate(inflater, container, false)
        initRecyclerView()
        initObserver()
        return binding.root
    }

    private fun initObserver() {
        categoryType?.let {
            viewmodel.getCategoriesByType(it)
                .observe(viewLifecycleOwner) { categoryList ->
                    Log.d(TAG, "Initial categories: ${categoryList.size}")
                    categoryAdapter.submitList(categoryList)
                }
        }
    }

    override fun onResume() {
        super.onResume()
        currentSelectedCategory?.let {
            Log.d(TAG, "Update selected category because of tab switched")
            categorySelectedListener?.onCategorySelected(it)
        }
    }

    private fun initRecyclerView() {
        categoryAdapter = CategoryAdapter() { category ->
            currentSelectedCategory = category
            categorySelectedListener?.onCategorySelected(category)
            Log.d(TAG, "选中类别: $category")
        }
        binding.categoryRecyclerView.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(requireContext(), 4)
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