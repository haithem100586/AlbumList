package com.android.albumlist.presentation.component.photo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.albumlist.data.Resource
import com.android.albumlist.data.error.Error.Companion.NETWORK_ERROR
import com.android.albumlist.domain.Photo
import com.android.albumlist.presentation.base.AlbumListViewModel
import com.android.albumlist.framework.Interactors
import com.android.albumlist.framework.db.PhotoEntity
import com.android.albumlist.util.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PhotosViewModel(application: Application, interactors: Interactors) :
    AlbumListViewModel(application, interactors) {

    val listPhotosViewStateMutableLiveData = MutableLiveData<PhotosViewState>()

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    private val openPhotoDetailsPrivate = MutableLiveData<Event<PhotoEntity>>()
    val _openPhotoDetails: LiveData<Event<PhotoEntity>> get() = openPhotoDetailsPrivate

    /* getPhotosFromDB */
    fun getPhotosFromDB() {
        listPhotosViewStateMutableLiveData.postValue(PhotosViewState(null,true,null))
        GlobalScope.launch {
            listPhotosViewStateMutableLiveData.postValue(PhotosViewState(interactors.getAllPhotosFromDB.invoke(),false,null))
        }
    }

    /* Call insertAllPhotos when getPhotosFromWS suceeded */
    fun getPhotosFromWS() {
        var serviceResponse: Resource<List<Photo>>
        listPhotosViewStateMutableLiveData.postValue(PhotosViewState(null,true,null))

        GlobalScope.launch {
            try {
                serviceResponse = interactors.getAllPhotosFromWS()
                listPhotosViewStateMutableLiveData.postValue(PhotosViewState(serviceResponse.data,false,null))
                serviceResponse.data?.let { interactors.insertAllPhotos(it) }
            } catch (e: Exception) {
                listPhotosViewStateMutableLiveData.postValue(PhotosViewState(null,false,NETWORK_ERROR))
            }
        }
    }

    /* openPhotoDetails */
    fun openPhotoDetails(photoEntity: PhotoEntity) {
        openPhotoDetailsPrivate.value = Event(photoEntity)
    }


}