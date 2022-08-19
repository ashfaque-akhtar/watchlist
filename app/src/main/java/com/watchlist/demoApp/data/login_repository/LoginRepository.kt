package com.watchlist.demoApp.data.login_repository

import androidx.lifecycle.LiveData
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.data.model.WatchList

interface LoginRepository {

    suspend fun fetchWatchList()
    suspend fun fetchWatchListData(watchListName:String)

    suspend fun getWatchLists():List<WatchList>

    suspend fun getWatchListData(watchListName:String,isAsc:Boolean,sortParam: String): LiveData<List<TradeDetail>>
}