package com.android.albumlist.interactors

import com.android.albumlist.data.PhotoRepository

class GetAllPhotosFromWS(private val photoRepository: PhotoRepository) {
     suspend operator fun invoke() = photoRepository.getAllPhotosFromWS()
}
