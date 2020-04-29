package com.android.albumlist.remote


import com.android.albumlist.util.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroClass {

    private fun getRetroInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()
    }

    fun getAPIInstance(): APIService {
        return getRetroInstance().create(APIService::class.java)
    }

}

