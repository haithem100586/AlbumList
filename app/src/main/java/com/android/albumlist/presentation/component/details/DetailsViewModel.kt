package com.android.albumlist.presentation.component.details

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.android.albumlist.framework.Interactors
import com.android.albumlist.framework.db.PhotoEntity
import com.android.albumlist.presentation.base.AlbumListViewModel


class DetailsViewModel(application: Application, interactors: Interactors) :
    AlbumListViewModel(application, interactors) {
    var photoEntity: MutableLiveData<PhotoEntity> = MutableLiveData()
}