package com.android.albumlist.framework

import com.android.albumlist.interactors.GetAllPhotosFromDB
import com.android.albumlist.interactors.GetAllPhotosFromWS
import com.android.albumlist.interactors.InsertAllPhotos

data class Interactors(
    val insertAllPhotos: InsertAllPhotos,
    val getAllPhotosFromWS: GetAllPhotosFromWS,
    val getAllPhotosFromDB: GetAllPhotosFromDB
)