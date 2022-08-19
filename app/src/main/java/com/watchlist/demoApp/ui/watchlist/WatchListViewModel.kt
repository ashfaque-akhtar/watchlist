package com.watchlist.demoApp.ui.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperations
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperationsImpl
import com.indusnet.watchlist.watchlist.repository.WatchListRepositoryImpl
import com.indusnet.watchlist.watchlist.repository.WatchlistRepository
import com.watchlist.demoApp.application.WatchListApplication
import com.watchlist.demoApp.constants.Constants.KEY_TRADE_PRICE
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.data.model.WatchList
import kotlinx.coroutines.launch

class WatchListViewModel() : ViewModel() {

    private val dao = WatchListApplication.instance.getDatabase().watchlistDao()
    private val databaseOperation: WatchListOperations = WatchListOperationsImpl(dao)

    private val repository: WatchlistRepository = WatchListRepositoryImpl(databaseOperation)

    private val _tradeDetailsData = MutableLiveData<List<TradeDetail>>()
    val tradeDetailsData: LiveData<List<TradeDetail>> get() = _tradeDetailsData

    /**
     * Load watchlist data from db
     */
    suspend fun loadWatchListData(
        selectedWatchList: String,
        isAsc: Boolean,
        sortPrams: String
    ) {
        var watchList = repository.getWatchListData(selectedWatchList, isAsc, sortPrams)

        if (sortPrams.equals(KEY_TRADE_PRICE)) {
            if (isAsc)
                watchList = watchList.sortedBy { it.LastTradePrice }
            else
                watchList = watchList.sortedByDescending { it.LastTradePrice }
        }
        _tradeDetailsData.postValue(watchList)
    }

    suspend fun getWatchLists(): List<WatchList> {
        return repository.getWatchLists()
    }

}