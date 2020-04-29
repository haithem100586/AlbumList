package com.android.albumlist.interactors

import com.android.albumlist.data.photo.local.LocalPhotoRepository

class GetAllPhotosFromDB(private val localPhotoRepository: LocalPhotoRepository) {
    suspend operator fun invoke() = localPhotoRepository.getAllFromDB()
}