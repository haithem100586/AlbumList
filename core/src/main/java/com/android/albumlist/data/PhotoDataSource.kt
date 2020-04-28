package com.android.albumlist.data

import com.android.albumlist.domain.Photo

interface PhotoDataSource {

    suspend fun getAllFromDB(): List<Photo>

    suspend fun getAllPhotosFromWS():Resource<List<Photo>>

    suspend fun insertAll(photos: List<Photo>)
}