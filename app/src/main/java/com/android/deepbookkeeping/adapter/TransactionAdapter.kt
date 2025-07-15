package com.android.deepbookkeeping.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.deepbookkeeping.data.constants.Constants
import com.android.deepbookkeeping.data.local.entity.Transaction
import com.android.deepbookkeeping.databinding.ItemTransactionBinding
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionAdapter :
    ListAdapter<Transaction, TransactionAdapter.TransactionViewHolder>(TransactionDiffCallback()) {

    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.apply {
                binding.categoryIconImageView.setImageResource(transaction.categoryResourceId)
                binding.amountTextView.text =
                    if (transaction.type == Constants.TRANSACTION_EXPENSE) "-¥" + transaction.amount else "¥" + transaction.amount
                binding.categoryTextView.text = transaction.category
                binding.dateTextView.text = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).format(transaction.date)
                binding.noteTextView.text = transaction.description
            }
        }
    }

    private class TransactionDiffCallback : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTransactionBinding.inflate(inflater, parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }
}