package com.indusnet.watchlist.watchlist.data.database.operation

import androidx.lifecycle.LiveData
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity

/**
 * Predefined decliration for WatchList Page
 */
interface WatchListOperations {
    suspend fun getWatchListNames(): List<WatchListEntity>
    suspend fun getWatchListData(watchListName: String): LiveData<List<TradeDetailEntity>>
    suspend fun getWatchListData(
        watchListName: String,
        isAsc: Boolean,
        sortParam: String
    ): List<TradeDetailEntity>

    suspend fun checkWatchListDataCount(watchListName: String): Int
    suspend fun updateWatchList(watchListData: List<WatchListEntity>)
    suspend fun updateWatchListData(watchListData: List<TradeDetailEntity>)
    suspend fun clearData()
}