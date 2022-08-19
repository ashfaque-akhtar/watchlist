package com.indusnet.watchlist.common.util

import androidx.core.graphics.createBitmap
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity
import com.watchlist.demoApp.data.model.TradeDetail
import com.watchlist.demoApp.data.model.WatchList


object Mapper {

    fun transformWatchList(inData: List<WatchListEntity>): List<WatchList> {
        return inData.map { WatchList(it.MwatchName) }
    }

    fun transformWatchListData(inData: List<TradeDetailEntity>): List<TradeDetail> {

        return inData.map {
            val newValue =  String.format("%.2f", DataConvertor.getRandomNewTradePrice(it.LastTradePrice)).toDouble()
           val  isPriceUp = newValue > it.LastTradePrice
            TradeDetail(it.Exch,it.ExchType,newValue,it.PClose,it.ShortName,DataConvertor.getAbsoluteValue(it.Volume),it.DayHigh,it.DayLow,it.NseBseCode,it.ScripCode,isPriceUp) }
    }

    fun addWatchListInEntity(inData: List<TradeDetailEntity>,watchlistName:String): List<TradeDetailEntity> {
       for(item in inData){
           item.watchlist=watchlistName.replace(" ","")
       }
        return inData
    }
}