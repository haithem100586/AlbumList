package com.android.albumlist.interactors

import com.android.albumlist.data.PhotoRepository
import com.android.albumlist.domain.Photo

class InsertAllPhotos(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(photos: List<Photo>) = photoRepository.insertAll(photos)
}