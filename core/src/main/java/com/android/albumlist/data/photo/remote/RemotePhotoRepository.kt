package com.android.albumlist.data.photo.remote

import com.android.albumlist.data.photo.local.LocalPhotoDataSource
import com.android.albumlist.domain.Photo

class RemotePhotoRepository(
    private val remotePhotoDataSource: RemotePhotoDataSource) {

    suspend fun getAllPhotosFromWS() = remotePhotoDataSource.getAllPhotosFromWS()
}