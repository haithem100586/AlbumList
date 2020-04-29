package com.android.albumlist.presentation.photo

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.albumlist.R
import com.android.albumlist.presentation.AlbumListViewModelFactory
import com.android.albumlist.presentation.base.BaseActivity
import com.task.data.error.Error.Companion.NETWORK_ERROR
import com.task.data.error.Error.Companion.NO_INTERNET_CONNECTION
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BaseActivity() {

    private lateinit var photoViewModel: PhotoViewModel
    private lateinit var photoAdapter: PhotoAdapter

    override val layoutId: Int get() = R.layout.activity_main
    override fun initializeViewModel() {
        photoViewModel = ViewModelProviders.of(
            this,
            AlbumListViewModelFactory
        ).get(PhotoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        getAllPhotos()
        callSearch()
        callPullToRefresh()
    }


    fun initView() {
        photoAdapter = PhotoAdapter() {}
        rv_photos.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_photos.adapter = photoAdapter
    }


    fun getAllPhotos() {

        photoViewModel.getPhotosFromDB()
        photoViewModel.listphotosMutableLiveData.observe(this, Observer {
            Timber.d("@@@ getPhotosFromDB : listphotosMutableLiveData size is %s", it.size)
            if (it.size > 0) {
                if (it.isEmpty() == false) {
                    it.let { it1 -> photoAdapter.update(it1) }
                    pb_home.visibility = View.GONE
                    sv_input.visibility = View.VISIBLE
                }
            } else {
                callServer()
            }
        })
    }


    private fun callServer() {
        if (photoViewModel.isNetworkAvailable(this)) {
            photoViewModel.getPhotosFromWS()
            photoViewModel.listResourcePhotosMutableLiveData.observe(this, Observer {
                Timber.d(
                    "@@@ getPhotosFromWS : listResourcePhotosMutableLiveData size is %d",
                    it.data?.size
                )
                if (it.errorCode != null) {
                    displayToast(it.errorCode!!)
                    pb_home.visibility = View.GONE
                    Timber.d("@@@ getPhotosFromWS : errorCode is %d", it.errorCode)
                } else if (it.data?.isEmpty() == false) {
                    it.data?.let { it1 -> photoAdapter.update(it1) }
                    pb_home.visibility = View.GONE
                    sv_input.visibility = View.VISIBLE
                }
            })
        } else {
            displayToast(NO_INTERNET_CONNECTION)
            pb_home.visibility = View.GONE
        }
    }


    fun callSearch() {
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

    fun callPullToRefresh() {
        srl_photos.setOnRefreshListener {
            srl_photos.isRefreshing = false
            sv_input.setQuery("", true)
            getAllPhotos()
        }
    }

    fun displayToast(error: Int) {
        var noInternetMsg = getString(R.string.msg_no_internet)
        if (error.equals(NETWORK_ERROR)) {
            noInternetMsg = getString(R.string.msg_problem_server)
        }
        Toast.makeText(this, noInternetMsg, Toast.LENGTH_SHORT).show()
    }
}
