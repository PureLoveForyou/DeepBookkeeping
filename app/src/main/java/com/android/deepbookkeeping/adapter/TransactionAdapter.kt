package com.android.deepbookkeeping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.relation.TransactionWithCategory
import com.android.deepbookkeeping.databinding.ItemTransactionBinding
import com.android.deepbookkeeping.utils.DateAndTimeUtils

class TransactionAdapter(private val onItemClick: ((item: TransactionWithCategory) -> Unit)) :
    ListAdapter<TransactionWithCategory, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TransactionWithCategory, onItemClick: (item: TransactionWithCategory) -> Unit) {
            binding.apply {
                binding.categoryIconImageView.setImageResource(item.category.iconResourceId)
                binding.amountTextView.text =
                    if (item.transaction.type == Constants.TRANSACTION_EXPENSE) "-¥" + item.transaction.amount else "¥" + item.transaction.amount
                binding.categoryTextView.text = item.category.name
                binding.dateTextView.text = DateAndTimeUtils.formatDateAndTime(item.transaction.date)
                binding.noteTextView.text = item.transaction.description
                root.setOnClickListener {
                    onItemClick(item)
                }
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<TransactionWithCategory>() {
        override fun areItemsTheSame(oldItem: TransactionWithCategory, newItem: TransactionWithCategory): Boolean {
            return oldItem.transaction.id == newItem.transaction.id
        }

        override fun areContentsTheSame(oldItem: TransactionWithCategory, newItem: TransactionWithCategory): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClick)
    }
}