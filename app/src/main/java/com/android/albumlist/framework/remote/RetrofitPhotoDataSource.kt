package com.android.albumlist.framework.remote

import com.android.albumlist.data.Resource
import com.android.albumlist.data.photo.remote.RemotePhotoDataSource
import com.android.albumlist.domain.Photo
import com.android.albumlist.remote.RetroClass
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RetrofitPhotoDataSource @Inject
constructor(private val retroClass: RetroClass) : RemotePhotoDataSource {

    override suspend fun getAllPhotosFromWS(): Resource<List<Photo>> {

        val apiService = retroClass.getAPIInstance()
        val photosCall = apiService.getPhotos()
        val response = photosCall.execute()

        return if (response.isSuccessful) {
            Timber.d("@@@ getAllPhotosFromWS : response.body().size is %d", response.body().size)
            Resource.Success(data = response.body())
        } else {
            Timber.d("@@@ getAllPhotosFromWS : response.message() : %s", response.message())
            Timber.d("@@@ getAllPhotosFromWS : response.errorBody() : %s", response.errorBody())
            Resource.DataError(errorCode = response.code())
        }

    }


}