package com.android.albumlist.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/* class database*/
@Database(
    entities = [PhotoEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AlbumListDataBase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "albumlist.db"

        private var instance: AlbumListDataBase? = null

        private fun create(context: Context): AlbumListDataBase =
            Room.databaseBuilder(context, AlbumListDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(context: Context): AlbumListDataBase =
            (instance ?: create(context)).also { instance = it }
    }

    abstract fun photoDao(): PhotoDao
}