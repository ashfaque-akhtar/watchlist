package com.watchlist.demoApp.ui.login

import androidx.lifecycle.ViewModel
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperations
import com.indusnet.watchlist.watchlist.data.database.operation.WatchListOperationsImpl
import com.indusnet.watchlist.watchlist.repository.WatchListRepositoryImpl
import com.indusnet.watchlist.watchlist.repository.WatchlistRepository
import com.watchlist.demoApp.application.WatchListApplication
import com.watchlist.demoApp.data.DataStoreHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val dao = WatchListApplication.instance.getDatabase().watchlistDao()
    private val databaseOperation: WatchListOperations = WatchListOperationsImpl(dao)

    private val repository: WatchlistRepository = WatchListRepositoryImpl(databaseOperation)

    suspend fun storeUserData(email: String) {
        repository.fetchWatchList()
        DataStoreHelper.saveUserData(email)
        DataStoreHelper.updateUserLoginStatus(true)
    }


    suspend fun logout() {
        repository.clearData()
        DataStoreHelper.clearData()
    }
}