package com.android.albumlist.util

import com.android.albumlist.domain.Photo
import com.google.gson.Gson
import java.io.File
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TestModelsGenerator {

    //private var photosModel: PhotosModel
    private var listPhotos: List<Photo>

    init {
        val gson = Gson()
        val jsonString = getJson("PhotosApiResponse.json")
        //photosModel = gson.fromJson(jsonString, PhotosModel::class.java)
        listPhotos = gson.fromJson(jsonString, Array<Photo>::class.java).toList()

    }

    fun generateListPhotos(): List<Photo> {
        return listPhotos
    }

    fun generatePhotosWithEmptyList(): List<Photo> {
        listPhotos = ArrayList()
        return listPhotos
    }


    fun getStupSearchTitle(): String {
        return listPhotos[0].title
    }


    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}
