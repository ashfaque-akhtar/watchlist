package com.indusnet.watchlist.watchlist.repository

import androidx.lifecycle.LiveData
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.data.model.WatchList

interface WatchlistRepository {

    suspend fun fetchWatchList()
    suspend fun fetchWatchListData(watchListName:String)

    suspend fun getWatchLists():List<WatchList>
    suspend fun getWatchListData(watchListName:String,isAsc:Boolean,sortParam: String):List<TradeDetail>

    suspend fun clearData()

}