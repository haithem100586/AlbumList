package com.android.albumlist.framework.db

import android.content.Context
import com.android.albumlist.data.PhotoDataSource
import com.android.albumlist.data.Resource
import com.android.albumlist.domain.Photo
import com.android.albumlist.remote.RetroClass
import timber.log.Timber

class ServicesPhotoDataSource(context: Context) : PhotoDataSource {

    private val photoDao = AlbumListDataBase.getInstance(context).photoDao()

    override suspend fun insertAll(photos: List<Photo>) {

        val myList: MutableList<PhotoEntity> = mutableListOf()
        //val myList: MutableList<PhotoEntity> = mutableListOf<PhotoEntity>()

        for (photo in photos) {
            myList.add(
                PhotoEntity(
                    photo.id,
                    photo.albumId,
                    photo.title,
                    photo.url,
                    photo.thumbnail
                )
            )
        }
        photoDao.insertAll(myList)
    }

    override suspend fun getAllFromDB(): List<Photo> = photoDao.getAll().map {
        Photo(
            it.id,
            it.albumId,
            it.title,
            it.url,
            it.thumbnail
        )
    }


    suspend override fun getAllPhotosFromWS(): Resource<List<Photo>> {

        val apiService = RetroClass().getAPIInstance()
        val photosCall = apiService.getPhotos()
        val response = photosCall.execute()

        if (response.isSuccessful) {
            Timber.d("@@@ getAllPhotosFromWS : response.body().size is %d", response.body().size)
            return Resource.Success(data = response.body())
        } else {
            Timber.d("@@@ getAllPhotosFromWS : response.message() : %s", response.message())
            Timber.d("@@@ getAllPhotosFromWS : response.errorBody() : %s", response.errorBody())
            return Resource.DataError(errorCode = response.code())
        }

    }


}