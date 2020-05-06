package com.android.albumlist.presentation.base.listeners

import com.android.albumlist.framework.db.PhotoEntity


interface RecyclerItemListener {
    fun onItemSelected(photoEntity: PhotoEntity)
}
