package com.example.binscope.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.binscope.databinding.HistoryItemBinding
import com.example.binscope.domain.model.CardData

class HistoryAdapter(private val showDetails: (CardData) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var historyItems: List<CardData> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = historyItems.size

    // TODO: Implement DiffUtil.Callback
    fun setItems(items: List<CardData>) {
        historyItems = items
        notifyDataSetChanged()
    }

    inner class HistoryViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CardData) {
            with(binding) {
                binTextView.text = item.bin
                seeDetails.setOnClickListener {
                    showDetails(item)
                }
            }
        }
    }
}