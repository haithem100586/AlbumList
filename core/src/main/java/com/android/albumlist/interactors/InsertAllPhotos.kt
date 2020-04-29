package com.android.albumlist.interactors

import com.android.albumlist.data.photo.local.LocalPhotoRepository
import com.android.albumlist.domain.Photo

class InsertAllPhotos(private val localPhotoRepository: LocalPhotoRepository) {
    suspend operator fun invoke(photos: List<Photo>) = localPhotoRepository.insertAll(photos)
}