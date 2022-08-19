package com.indusnet.watchlist.watchlist.data.database.operation

import androidx.lifecycle.LiveData
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity
import com.watchlist.demoApp.constants.Constants
import com.watchlist.demoApp.data.room.WatchListDao


class WatchListOperationsImpl(private val dao: WatchListDao) : WatchListOperations {
    override suspend fun getWatchListNames(): List<WatchListEntity> {
        return dao.getWatchLists()
    }

    override suspend fun getWatchListData(watchListName: String): LiveData<List<TradeDetailEntity>> {
        return dao.getWatchListData(watchListName)
    }

    override suspend fun getWatchListData(
        watchListName: String,
        isAsc: Boolean,
        sortParam: String
    ): List<TradeDetailEntity> {

        if(sortParam==Constants.KEY_VOLUME){
            return dao.getWatchListDataVolume(watchListName, isAsc)
        }

        if(sortParam== Constants.KEY_PCLOSE){
            return dao.getWatchListDataPClose(watchListName, isAsc)
        }

        return dao.getWatchListDataLastTradePrice(watchListName, isAsc)
    }

    override suspend fun checkWatchListDataCount(watchListName: String): Int {
        return dao.getWatchListDataSize(watchListName)
    }

    override suspend fun updateWatchList(watchList: List<WatchListEntity>) {
        dao.insertWatchLists(watchList)
    }

    override suspend fun updateWatchListData(watchListData: List<TradeDetailEntity>) {
        dao.insertWatchlistData(watchListData)
    }

    override suspend fun clearData() {
        dao.cleaWatchlist()
        dao.clearWatchListData()
    }

}