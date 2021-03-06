package com.android.albumlist.presentation.component.photo

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.test.espresso.IdlingResource
import com.android.albumlist.R
import com.android.albumlist.framework.db.PhotoEntity
import com.android.albumlist.presentation.ViewModelFactory
import com.android.albumlist.presentation.base.BaseActivity
import com.android.albumlist.presentation.component.details.DetailsActivity
import com.android.albumlist.util.Constants
import com.android.albumlist.util.Event
import com.android.albumlist.util.espresso.EspressoIdlingResource
import com.android.albumlist.util.observeEvent
import com.android.albumlist.data.error.Error.Companion.NETWORK_ERROR
import com.android.albumlist.data.error.Error.Companion.NO_INTERNET_CONNECTION
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var photosViewModel: PhotosViewModel
    private lateinit var photoAdapter: PhotoAdapter

    override val layoutId: Int get() = R.layout.activity_main

    val countingIdlingResource: IdlingResource
        @VisibleForTesting
        get() = EspressoIdlingResource.idlingResource

    override fun initializeViewModel() {
        photosViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory
        ).get(PhotosViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        getAllPhotos()
        callSearch()
        callPullToRefresh()
    }


    private fun initView() {
        photoAdapter = PhotoAdapter(photosViewModel) /*{}*/
        rv_photos.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_photos.adapter = photoAdapter
    }


    private fun getAllPhotos() {
        photosViewModel.getPhotosFromDB()
        photosViewModel.listPhotosViewStateMutableLiveData.observe(this, Observer {
            Timber.d("@@@ getPhotosFromDB : listphotosMutableLiveData size is %s", it.photos?.size)
            if (!it.loading) {
                if (it.photos?.size!=0) {
                    it.photos?.let { it1 -> photoAdapter.update(it1) }
                    pb_home.visibility = View.GONE
                    sv_input.visibility = View.VISIBLE
                }else {
                    callServer()
                }
            }
        })
    }


    private fun callServer() {
        if (photosViewModel.isNetworkAvailable(this)) {
            photosViewModel.getPhotosFromWS()
            photosViewModel.listPhotosViewStateMutableLiveData.observe(this, Observer {
                Timber.d(
                    "@@@ getPhotosFromWS : listResourcePhotosMutableLiveData size is %d",
                    it.photos?.size
                )
                if (it.errorCode != 0) {
                    it.errorCode?.let { it1 -> displayToast(it1) }
                    pb_home.visibility = View.GONE
                    Timber.d("@@@ getPhotosFromWS : errorCode is %d", it.errorCode)
                } else if (it.photos?.isEmpty() == false) {
                    it.photos.let { it1 -> it1?.let { it2 -> photoAdapter.update(it2) } }
                    pb_home.visibility = View.GONE
                    sv_input.visibility = View.VISIBLE
                }
            })
        } else {
            displayToast(NO_INTERNET_CONNECTION)
            pb_home.visibility = View.GONE
        }
    }


    private fun callSearch() {
        // Associate search_layout configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv_input.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        sv_input.maxWidth = Integer.MAX_VALUE
        // listening to search query text change
        sv_input.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                photoAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                photoAdapter.filter.filter(query)
                return false
            }
        })
    }

    private fun callPullToRefresh() {
        srl_photos.setOnRefreshListener {
            srl_photos.isRefreshing = false
            sv_input.setQuery("", true)
            getAllPhotos()
        }
    }

    private fun displayToast(error: Int) {
        var noInternetMsg = getString(R.string.msg_no_internet)
        if (error == NETWORK_ERROR) {
            noInternetMsg = getString(R.string.msg_problem_server)
        }
        Toast.makeText(this, noInternetMsg, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToDetailsScreen(navigateEvent: Event<PhotoEntity>) {
        navigateEvent.getContentIfNotHandled()?.let {
            startActivity(intentFor<DetailsActivity>(Constants.PHOTOS_ITEM_KEY to it))
        }
    }

    override fun observeViewModel() {
        // :: converts a kotlin function into a lambda
        observeEvent(photosViewModel._openPhotoDetails, ::navigateToDetailsScreen)
    }
}
