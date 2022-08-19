package com.indusnet.watchlist.watchlist.data.network.dto

import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity

data class NetworkResponseWatchListData(
    val Data: List<TradeDetailEntity>,
    val MarketWatchName: String,
    val Message: String,
    val Status: Int
)