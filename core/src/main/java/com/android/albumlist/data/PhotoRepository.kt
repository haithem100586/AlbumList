package com.android.albumlist.data

import com.android.albumlist.domain.Photo

class PhotoRepository(

    private val photoDataSource: PhotoDataSource) {

    suspend fun getAllFromDB() = photoDataSource.getAllFromDB()

    suspend fun getAllPhotosFromWS() = photoDataSource.getAllPhotosFromWS()

    suspend fun insertAll(photos: List<Photo>) = photoDataSource.insertAll(photos)

}