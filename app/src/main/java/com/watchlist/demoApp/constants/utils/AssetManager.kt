package com.indusnet.watchlist.common.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity
import com.indusnet.watchlist.watchlist.data.network.dto.NetworkResponseWatchList
import com.indusnet.watchlist.watchlist.data.network.dto.NetworkResponseWatchListData
import com.watchlist.demoApp.application.WatchListApplication
import java.io.IOException
import java.util.*

object AssetManager {

    suspend fun getWatchListDataFromAssets(watchlistName:String): List<TradeDetailEntity> {
        val fileName= watchlistName.replace(" ", "").lowercase(Locale.ROOT).plus("_data.json")
        lateinit var jsonString: String
        try {
            jsonString = WatchListApplication.instance.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("Debug",ioException.message?:"")
        }


        val response=Gson().fromJson(jsonString,NetworkResponseWatchListData::class.java)
        return response.Data

    }

    suspend fun getWatchListsFromAssets(): List<WatchListEntity> {
        val fileName="watchlist_names.json"
        lateinit var jsonString: String
        try {
            jsonString = WatchListApplication.instance.assets.open(fileName)
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.d("Debug",ioException.message?:"")
        }

        val response=Gson().fromJson(jsonString,NetworkResponseWatchList::class.java)
        return response.MWName
    }
}