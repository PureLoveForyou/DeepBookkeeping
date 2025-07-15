package com.android.deepbookkeeping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.deepbookkeeping.data.local.entity.Category
import com.android.deepbookkeeping.databinding.CategoryItemRecyclerItemBinding

class CategoryAdapter(private val onItemClickListener: ((Category) -> Unit)) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    class CategoryViewHolder(private val binding: CategoryItemRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, onItemClick: (Category) -> Unit) {
            binding.apply {
                categoryIcon.setImageResource(category.iconResourceId)
                categoryTitle.text = category.name
                root.setOnClickListener {
                    onItemClick(category)
                }
            }
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CategoryItemRecyclerItemBinding.inflate(inflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}