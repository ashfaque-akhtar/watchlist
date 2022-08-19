package com.watchlist.demoApp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.indusnet.watchlist.watchlist.data.database.entity.TradeDetailEntity
import com.indusnet.watchlist.watchlist.data.database.entity.WatchListEntity
import com.watchlist.demoApp.constants.Constants

@Database(entities = [WatchListEntity::class,TradeDetailEntity::class], version = 1)
abstract class WatchlistDatabase:RoomDatabase() {

    abstract fun watchlistDao(): WatchListDao

    companion object {
        private var instance: WatchlistDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): WatchlistDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx.applicationContext, WatchlistDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()

            return instance!!

        }
    }
}