package com.android.albumlist.domain

import java.io.Serializable

data class Photo(
    var id: Int=0,
    var albumId: Int=0,
    var title: String="",
    var url: String="",
    var thumbnailUrl: String=""
): Serializable {
    companion object {
        val EMPTY = Photo(0, 0, "", "", "")
    }
}
