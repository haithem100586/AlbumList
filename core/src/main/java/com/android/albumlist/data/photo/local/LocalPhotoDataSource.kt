package com.android.albumlist.data.photo.local

import com.android.albumlist.data.Resource
import com.android.albumlist.domain.Photo

interface LocalPhotoDataSource {
    suspend fun getAllFromDB(): List<Photo>
    suspend fun insertAll(photos: List<Photo>)
}