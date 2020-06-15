package com.android.albumlist.interactors

import com.android.albumlist.data.photo.remote.RemotePhotoRepository

class GetAllPhotosFromWS(private val remotePhotoRepository: RemotePhotoRepository) {
     suspend operator fun invoke() = remotePhotoRepository.getAllPhotosFromWS()
}
