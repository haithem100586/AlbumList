package com.android.albumlist.framework.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "photo")
data class PhotoEntity(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "albumId") val albumId: Int = 0,
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "url") val url: String = "",
    @ColumnInfo(name = "thumbnail") val thumbnail: String = ""
)
