package com.android.albumlist.domain

import java.io.Serializable

data class Photo(
    val id: Int=0,
    val albumId: Int=0,
    val title: String="",
    val url: String="",
    val thumbnailUrl: String=""
): Serializable {
    companion object {
        val EMPTY = Photo(0, 0, "", "", "")
    }
}
