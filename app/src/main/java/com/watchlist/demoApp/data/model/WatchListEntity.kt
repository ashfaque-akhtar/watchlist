package com.indusnet.watchlist.watchlist.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WatchListEntity(
    @ColumnInfo(name = "name")
    @PrimaryKey
    val MwatchName: String
)