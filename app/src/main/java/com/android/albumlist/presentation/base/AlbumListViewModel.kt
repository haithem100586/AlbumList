package com.android.albumlist.presentation.base

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.android.albumlist.framework.Interactors
import com.android.albumlist.util.ConnectivityHelper


open class AlbumListViewModel(application: Application, protected val interactors: Interactors) :
    AndroidViewModel(application) {

    open fun isNetworkAvailable(context: Context): Boolean {
        val connectivityHelper = ConnectivityHelper()
        if (connectivityHelper.isConnectedToNetwork(context)) run {
            return true
        } else {
            return false
        }
    }

}
