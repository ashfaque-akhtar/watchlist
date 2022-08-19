package com.watchlist.demoApp.data.model

import androidx.room.ColumnInfo
import java.io.Serializable

data class TradeDetail(
    val Exch: String,
    val ExchType: String,
    val LastTradePrice: Double,
   // val NewTradePrice: Double,
    val PClose: Double,
    val ShortName: String,
    val Volume: String,
    val DayHigh: Double,
    val DayLow: Double,
    val NseBseCode: Int,
    val ScripCode: Int,
    val priceUp : Boolean
):Serializable