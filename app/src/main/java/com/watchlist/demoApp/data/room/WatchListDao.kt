package com.watchlist.demoApp.data.room

import android.database.sqlite.SQLiteQuery
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchLists(watchList: List<WatchListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlistData(tradeDetails: List<TradeDetailEntity>)

    @Query("SELECT * FROM WatchListEntity")
    suspend fun getWatchLists(): List<WatchListEntity>

    @Query("SELECT * FROM TradeDetailEntity WHERE watchlist= :watchListName")
    fun getWatchListData(watchListName:String): LiveData<List<TradeDetailEntity>>

    @Query("SELECT * FROM TradeDetailEntity WHERE watchlist= :watchListName ORDER BY CASE WHEN :isAsc=1 THEN last_trade_price END ASC, CASE WHEN :isAsc=0 THEN last_trade_price END DESC")
    fun getWatchListDataLastTradePrice(watchListName:String, isAsc:Boolean): List<TradeDetailEntity>

    @RawQuery(observedEntities = [TradeDetailEntity::class])
    fun getWatchListData(query:SupportSQLiteQuery): LiveData<List<TradeDetailEntity>>

    @Query("SELECT COUNT(*) FROM TradeDetailEntity WHERE watchlist= :watchListName")
    suspend fun getWatchListDataSize(watchListName:String): Int

    @Query("DELETE FROM WatchListEntity")
    suspend fun cleaWatchlist()

    @Query("DELETE FROM TradeDetailEntity")
    suspend fun clearWatchListData()

    @Query("SELECT * FROM TradeDetailEntity WHERE watchlist= :watchListName ORDER BY CASE WHEN :isAsc=1 THEN p_close END ASC, CASE WHEN :isAsc=0 THEN p_close END DESC")
    fun getWatchListDataPClose(watchListName:String, isAsc:Boolean): List<TradeDetailEntity>

    @Query("SELECT * FROM TradeDetailEntity WHERE watchlist= :watchListName ORDER BY CASE WHEN :isAsc=1 THEN volume END ASC, CASE WHEN :isAsc=0 THEN volume END DESC")
    fun getWatchListDataVolume(watchListName:String, isAsc:Boolean): List<TradeDetailEntity>
}