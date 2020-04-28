package com.android.albumlist

import android.app.Application
import com.android.albumlist.data.PhotoRepository
import com.android.albumlist.framework.Interactors
import com.android.albumlist.framework.db.ServicesPhotoDataSource
import com.android.albumlist.interactors.GetAllPhotosFromDB
import com.android.albumlist.interactors.GetAllPhotosFromWS
import com.android.albumlist.interactors.InsertAllPhotos
import com.android.albumlist.presentation.AlbumListViewModelFactory


class AlbumListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val photoRepository = PhotoRepository(
            ServicesPhotoDataSource(this)
        )

        /*
        This injects all the dependencies into AlbumListViewModelFactory.
        It creates ViewModels in the app and passes interactor dependencies to them.
        */
        AlbumListViewModelFactory.inject(
            this,
            Interactors(
                InsertAllPhotos(photoRepository),
                GetAllPhotosFromWS(photoRepository),
                GetAllPhotosFromDB(photoRepository)
            )
        )
    }
}