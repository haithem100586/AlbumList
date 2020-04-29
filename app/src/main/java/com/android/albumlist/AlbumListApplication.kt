package com.android.albumlist

import android.app.Application
import com.android.albumlist.data.photo.local.LocalPhotoRepository
import com.android.albumlist.data.photo.remote.RemotePhotoRepository
import com.android.albumlist.framework.Interactors
import com.android.albumlist.framework.db.RoomPhotoDataSource
import com.android.albumlist.framework.remote.RetrofitPhotoDataSource
import com.android.albumlist.interactors.GetAllPhotosFromDB
import com.android.albumlist.interactors.GetAllPhotosFromWS
import com.android.albumlist.interactors.InsertAllPhotos
import com.android.albumlist.presentation.AlbumListViewModelFactory


class AlbumListApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val localPhotoRepository = LocalPhotoRepository(RoomPhotoDataSource(this))
        val remotePhotoRepository = RemotePhotoRepository(RetrofitPhotoDataSource())

        /*
        This injects all the dependencies into AlbumListViewModelFactory.
        It creates ViewModels in the app and passes interactor dependencies to them.
        */
        AlbumListViewModelFactory.inject(
            this,
            Interactors(
                InsertAllPhotos(localPhotoRepository),
                GetAllPhotosFromWS(remotePhotoRepository),
                GetAllPhotosFromDB(localPhotoRepository)
            )
        )
    }
}