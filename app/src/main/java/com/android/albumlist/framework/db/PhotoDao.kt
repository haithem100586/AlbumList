package com.android.albumlist.framework.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface PhotoDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("select * from photo")
    fun getAll(): List<PhotoEntity>
}