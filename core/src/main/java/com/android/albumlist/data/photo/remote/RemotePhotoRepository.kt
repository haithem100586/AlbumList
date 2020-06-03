package com.android.albumlist.data.photo.remote


class RemotePhotoRepository(
    private val remotePhotoDataSource: RemotePhotoDataSource) {
    suspend fun getAllPhotosFromWS() = remotePhotoDataSource.getAllPhotosFromWS()
}