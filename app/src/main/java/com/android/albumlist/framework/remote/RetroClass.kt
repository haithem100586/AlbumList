package com.android.albumlist.remote


import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroClass {

    companion object {
        val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    private fun getRetroInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()
    }

    fun getAPIInstance(): APIService {
        return getRetroInstance().create(APIService::class.java)
    }

}

