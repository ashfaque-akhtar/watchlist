package com.indusnet.watchlist.watchlist.data.network.dto

import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity

data class NetworkResponseWatchList(
    val MWName: List<WatchListEntity>,
    val Status: Int
)