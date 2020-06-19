package com.android.albumlist.presentation.component.photo

import com.android.albumlist.domain.Photo

data class PhotosViewState(
    val photos:List<Photo>?,
    val loading:Boolean,
    val errorMessage: Int?
)