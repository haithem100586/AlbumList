package com.android.albumlist.presentation.component.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.android.albumlist.R
import com.android.albumlist.framework.db.PhotoEntity
import com.android.albumlist.presentation.ViewModelFactory
import com.android.albumlist.presentation.base.BaseActivity
import com.android.albumlist.util.Constants
import com.android.albumlist.util.observe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_layout.*


class DetailsActivity : BaseActivity() {

    private lateinit var detailsViewModel: DetailsViewModel

    override val layoutId: Int
        get() = R.layout.details_layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailsViewModel.photoEntity.value = intent.getParcelableExtra(Constants.PHOTOS_ITEM_KEY)
    }

    override fun initializeViewModel() {
        detailsViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory
        ).get(DetailsViewModel::class.java)
    }

    override fun observeViewModel() {
        observe(detailsViewModel.photoEntity, ::initializeView)
    }

    private fun initializeView(photoEntity: PhotoEntity) {
        tv_title?.text = photoEntity.title
        tv_thumbnailUrl?.text = photoEntity.thumbnail
        if (!photoEntity.url.isEmpty()) {
            Picasso.get().load(photoEntity.url).placeholder(R.drawable.ic_launcher_background)
                .into(iv_photo_main_Image)
        }
    }
}
