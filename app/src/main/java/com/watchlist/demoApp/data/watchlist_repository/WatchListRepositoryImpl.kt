package com.indusnet.watchlist.watchlist.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.indusnet.watchlist.common.util.AssetManager
import com.indusnet.watchlist.common.util.Mapper
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperations
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.data.model.WatchList

class WatchListRepositoryImpl(private val databaseOperation : WatchListOperations):WatchlistRepository {
    override suspend fun fetchWatchList() {
        val watchLists= AssetManager.getWatchListsFromAssets()
        databaseOperation.updateWatchList(watchLists)
    }

    override suspend fun fetchWatchListData(watchListName: String) {
        val watchListData=AssetManager.getWatchListDataFromAssets(watchListName)
        databaseOperation.updateWatchListData(Mapper.addWatchListInEntity(watchListData,watchListName))
    }

    override suspend fun getWatchLists(): List<WatchList> {
        val result = databaseOperation.getWatchListNames()
        return Mapper.transformWatchList(result)
    }

    override suspend fun getWatchListData(
        watchListName: String,
        isAsc: Boolean,
        sortParam: String
    ): List<TradeDetail> {
        if(databaseOperation.checkWatchListDataCount(watchListName)==0){
            fetchWatchListData(watchListName)
        }
        val result = databaseOperation.getWatchListData(watchListName,isAsc,sortParam)
        return Mapper.transformWatchListData(result)
    }

    override suspend fun clearData() {
        databaseOperation.clearData()
    }
}