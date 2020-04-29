package com.android.albumlist.data.photo.local

import com.android.albumlist.data.photo.local.LocalPhotoDataSource
import com.android.albumlist.domain.Photo

class LocalPhotoRepository(
    private val localPhotoDataSource: LocalPhotoDataSource) {

    suspend fun getAllFromDB() = localPhotoDataSource.getAllFromDB()
    suspend fun insertAll(photos: List<Photo>) = localPhotoDataSource.insertAll(photos)
}