package com.android.albumlist.framework.db

import android.content.Context
import com.android.albumlist.data.photo.local.LocalPhotoDataSource
import com.android.albumlist.domain.Photo
import javax.inject.Inject
import javax.inject.Singleton


class RoomPhotoDataSource(context: Context) : LocalPhotoDataSource {

    private val photoDao = AlbumListDataBase.getInstance(context).photoDao()

    override suspend fun insertAll(photos: List<Photo>) {

        val myList: MutableList<PhotoEntity> = mutableListOf()

        for (photo in photos) {
            myList.add(
                PhotoEntity(
                    photo.id,
                    photo.albumId,
                    photo.title,
                    photo.url,
                    photo.thumbnail
                )
            )
        }
        photoDao.insertAll(myList)
    }

    override suspend fun getAllFromDB(): List<Photo> = photoDao.getAll().map {
        Photo(
            it.id,
            it.albumId,
            it.title,
            it.url,
            it.thumbnail
        )
    }

}