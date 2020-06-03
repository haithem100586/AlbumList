package com.android.albumlist

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.android.albumlist.data.photo.local.LocalPhotoRepository
import com.android.albumlist.data.photo.remote.RemotePhotoRepository
import com.android.albumlist.framework.Interactors
import com.android.albumlist.framework.db.RoomPhotoDataSource
import com.android.albumlist.framework.di.DaggerAppComponent
import com.android.albumlist.framework.remote.RetrofitPhotoDataSource
import com.android.albumlist.interactors.GetAllPhotosFromDB
import com.android.albumlist.interactors.GetAllPhotosFromWS
import com.android.albumlist.interactors.InsertAllPhotos
import com.android.albumlist.presentation.ViewModelFactory
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


open class App : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var retrofitPhotoDataSource: RetrofitPhotoDataSource

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>



    override fun onCreate() {
        super.onCreate()
        initDagger()
        initViewModelFactory()
    }

    open fun initDagger() {
        DaggerAppComponent.builder().build().inject(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector


    fun initViewModelFactory(){

        val localPhotoRepository = LocalPhotoRepository(RoomPhotoDataSource(this))
        val remotePhotoRepository = RemotePhotoRepository(retrofitPhotoDataSource)
        /*
        This injects all the dependencies into AlbumListViewModelFactory.
        It creates ViewModels in the app and passes interactor dependencies to them.
        */
        ViewModelFactory.inject(
            this,
            Interactors(
                InsertAllPhotos(localPhotoRepository),
                GetAllPhotosFromWS(remotePhotoRepository),
                GetAllPhotosFromDB(localPhotoRepository)
            )
        )
    }

}