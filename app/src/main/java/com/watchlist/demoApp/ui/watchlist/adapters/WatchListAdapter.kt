package com.watchlist.demoApp.ui.watchlist.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.databinding.ItemTradeBinding

class WatchListAdapter(val onSelect: (TradeDetail) -> Unit) :
    RecyclerView.Adapter<WatchListAdapter.WatchListViewHolder>() {

    private var watchListData = mutableListOf<TradeDetail>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListViewHolder {
        val binding = ItemTradeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WatchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchListViewHolder, position: Int) {
        holder.bind(watchListData[position], onSelect)
    }

    override fun getItemCount(): Int = watchListData.size


    class WatchListViewHolder(private val binding: ItemTradeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tradeData: TradeDetail, onSelect: (TradeDetail) -> Unit) {
            binding.tradeDetail = tradeData

            binding.root.setOnClickListener {
                onSelect(tradeData)
            }
        }
    }


    fun updateWatchList(watchListData: List<TradeDetail>) {

        this.watchListData = watchListData.toMutableList()
        // this.watchListData.addAll(watchListData)

        notifyDataSetChanged()
    }
}
