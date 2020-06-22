package com.android.albumlist.presentation.component.photo

import com.android.albumlist.domain.Photo

data class PhotosViewState(
    var photos:List<Photo>?,
    val loading:Boolean,
    var errorCode: Int?
)