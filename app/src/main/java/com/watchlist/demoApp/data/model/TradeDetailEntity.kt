package com.indusnet.watchlist.watchlist.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TradeDetailEntity(
    @ColumnInfo(name = "full_name")
    @PrimaryKey
    val FullName: String,
    @ColumnInfo(name = "day_high")
    val DayHigh: Double,
    @ColumnInfo(name = "day_low")
    val DayLow: Double,
    @ColumnInfo(name = "eps")
    val EPS: Int,
    @ColumnInfo(name = "exch")
    val Exch: String,
    @ColumnInfo(name = "exch_type")
    val ExchType: String,
    @ColumnInfo(name = "high_52_week")
    val High52Week: Int,
    @ColumnInfo(name = "last_trade_price")
    val LastTradePrice: Double,
    @ColumnInfo(name = "last_trade_time")
    val LastTradeTime: String,
    @ColumnInfo(name = "low_52_week")
    val Low52Week: Int,
    @ColumnInfo(name = "month")
    val Month: Int,
    @ColumnInfo(name = "name")
    val Name: String,
    @ColumnInfo(name = "nse_bse_code")
    val NseBseCode: Int,
    @ColumnInfo(name = "p_close")
    val PClose: Double,
    @ColumnInfo(name = "quarter")
    val Quarter: Int,
    @ColumnInfo(name = "scrip_code")
    val ScripCode: Int,
    @ColumnInfo(name = "short_name")
    val ShortName: String,
    @ColumnInfo(name = "volume")
    val Volume: Int,
    @ColumnInfo(name = "year")
    val Year: Int,
    @ColumnInfo(name = "watchlist")
    var watchlist: String
)