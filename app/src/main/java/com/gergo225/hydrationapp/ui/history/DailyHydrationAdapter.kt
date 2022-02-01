package com.gergo225.hydrationapp.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gergo225.hydrationapp.databinding.ListItemDailyHydrationBinding

class DailyHydrationAdapter() :
    ListAdapter<DailyHydrationItem, DailyHydrationAdapter.ViewHolder>(DailyHydrationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ListItemDailyHydrationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyHydrationItem) {
            binding.dailyHydrationItem = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemDailyHydrationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class DailyHydrationDiffCallback : DiffUtil.ItemCallback<DailyHydrationItem>() {
        override fun areItemsTheSame(
            oldItem: DailyHydrationItem,
            newItem: DailyHydrationItem
        ): Boolean {
            return oldItem.dayId == newItem.dayId
        }

        override fun areContentsTheSame(
            oldItem: DailyHydrationItem,
            newItem: DailyHydrationItem
        ): Boolean {
            return oldItem == newItem
        }

    }

}