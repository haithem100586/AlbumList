package com.android.albumlist.presentation.component.photo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.albumlist.data.Resource
import com.android.albumlist.domain.Photo
import com.android.albumlist.presentation.base.AlbumListViewModel
import com.android.albumlist.framework.Interactors
import com.task.data.error.Error.Companion.NETWORK_ERROR
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PhotoViewModel(application: Application, interactors: Interactors) :
    AlbumListViewModel(application, interactors) {

    val listResourcePhotosMutableLiveData = MutableLiveData<Resource<List<Photo>>>()
    val listphotosMutableLiveData = MutableLiveData<List<Photo>>()


    fun getPhotosFromDB() {
        GlobalScope.launch {
            listphotosMutableLiveData.postValue(interactors.getAllPhotosFromDB.invoke())
        }
    }

    fun getPhotosFromWS() {
        var serviceResponse: Resource<List<Photo>>
        listResourcePhotosMutableLiveData.postValue(Resource.Loading())

        GlobalScope.launch {
            try {
                serviceResponse = interactors.getAllPhotosFromWS()
                listResourcePhotosMutableLiveData.postValue(serviceResponse)
                serviceResponse.data?.let { interactors.insertAllPhotos(it) }
            } catch (e: Exception) {
                listResourcePhotosMutableLiveData.postValue(Resource.DataError(NETWORK_ERROR))
            }
        }
    }


}