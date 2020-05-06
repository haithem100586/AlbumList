package com.android.albumlist.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.albumlist.framework.Interactors
import com.android.albumlist.presentation.base.AlbumListViewModel


object ViewModelFactory : ViewModelProvider.Factory {

    lateinit var application: Application
    lateinit var dependencies: Interactors

    fun inject(application: Application, dependencies: Interactors) {
        ViewModelFactory.application = application
        ViewModelFactory.dependencies = dependencies
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (AlbumListViewModel::class.java.isAssignableFrom(modelClass)) {
            return modelClass.getConstructor(Application::class.java, Interactors::class.java)
                .newInstance(
                    application,
                    dependencies
                )
        } else {
            throw IllegalStateException("ViewModel must extend AlbumListViewModel")
        }
    }

}