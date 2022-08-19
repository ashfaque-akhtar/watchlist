package com.watchlist.demoApp.ui.splash

import androidx.lifecycle.ViewModel
import com.watchlist.demoApp.data.DataStoreHelper

class SplashViewModel() : ViewModel() {

    suspend fun getLoginStatus() : Boolean = DataStoreHelper.isUserLoggedIn()
}