package com.android.albumlist.interactors

import com.android.albumlist.data.PhotoRepository

class GetAllPhotosFromDB(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke() = photoRepository.getAllFromDB()
}