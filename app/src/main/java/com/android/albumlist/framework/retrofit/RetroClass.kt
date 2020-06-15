package com.android.albumlist.framework.retrofit


import com.android.albumlist.util.Constants
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetroClass @Inject
constructor(private val gson: Gson) {

    private fun getRetroInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create(gson)
        ).build()
    }

    fun getAPIInstance(): APIService {
        return getRetroInstance().create(APIService::class.java)
    }

}

