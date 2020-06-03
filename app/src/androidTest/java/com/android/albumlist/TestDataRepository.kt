package com.android.albumlist

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.android.albumlist.TestDataRepository.Instance.initData
import com.android.albumlist.data.Resource
import com.android.albumlist.data.photo.remote.RemotePhotoDataSource
import com.android.albumlist.domain.Photo
import com.google.gson.Gson
import java.io.InputStream
import javax.inject.Inject



class TestDataRepository @Inject constructor() : RemotePhotoDataSource {

    override suspend fun getAllPhotosFromWS(): Resource<List<Photo>> {
        return Resource.Success(initData())
    }

    object Instance {
        var STATUS = DATA_STATUS.FULL_LIST
        fun initData(): List<Photo> {
            val gson = Gson()
            val jsonString = getJson("PhotosApiResponse.json")
            return gson.fromJson(jsonString, Array<Photo>::class.java).toList()
        }

        private fun getJson(path: String): String {
            val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
            val inputStream: InputStream = ctx.assets.open(path)
            return inputStream.bufferedReader().use { it.readText() }
        }
    }

}

enum class DATA_STATUS { EMPTY_LIST, FULL_LIST, NO_DATA, NO_INTERNET }
