package com.watchlist.demoApp.application

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.watchlist.demoApp.data.DataStoreHelper
import com.watchlist.demoApp.data.room.WatchlistDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WatchListApplication : Application() {

    companion object {
        lateinit var instance: WatchListApplication
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "applicationPreference")
    private lateinit var database: WatchlistDatabase
    override fun onCreate() {
        super.onCreate()
        setDefaultTheme()
        instance = this

        database = WatchlistDatabase.getInstance(this)
    }

    /**
     * Returns preference instance for the whole app
     */
    fun getDataStore(): DataStore<Preferences> {
        return dataStore
    }

    /**
     * Returns database instance for the whole app
     */
    fun getDatabase(): WatchlistDatabase {
        return database
    }

    /**
     * Returns Default Theme
     */
    private fun setDefaultTheme() = CoroutineScope(Dispatchers.Main).launch {
        val theme = withContext(Dispatchers.IO) { DataStoreHelper.getAppTheme() }
        AppCompatDelegate.setDefaultNightMode(theme)

    }

    /**
     * change app theme by overriding default them
     */
    fun changeAppTheme(theme: Int) = CoroutineScope(Dispatchers.Main).launch {
        AppCompatDelegate.setDefaultNightMode(theme)
        CoroutineScope(Dispatchers.IO).launch { DataStoreHelper.saveAppTheme(theme) }
    }
}