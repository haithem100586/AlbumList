package com.android.albumlist.framework.retrofit

import retrofit2.Call
import com.android.albumlist.domain.Photo
import retrofit2.http.*


interface APIService {
    @GET("photos")
    fun getPhotos(): Call<List<Photo>>
}