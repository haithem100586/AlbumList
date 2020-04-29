package com.android.albumlist.data.photo.remote

import com.android.albumlist.data.Resource
import com.android.albumlist.domain.Photo

interface RemotePhotoDataSource {
    suspend fun getAllPhotosFromWS(): Resource<List<Photo>>
}