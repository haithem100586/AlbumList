package com.android.albumlist.presentation.component.splash

import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.android.albumlist.R
import com.android.albumlist.presentation.AlbumListViewModelFactory
import com.android.albumlist.presentation.base.BaseActivity
import com.android.albumlist.presentation.component.photo.MainActivity
import com.android.albumlist.util.Constants
import org.jetbrains.anko.startActivity


class SplashActivity : BaseActivity() {

    private lateinit var splashViewModel: SplashViewModel

    override val layoutId: Int
        get() = R.layout.splash_layout

    override fun initializeViewModel() {
        splashViewModel = ViewModelProviders.of(
            this,
            AlbumListViewModelFactory
        ).get(SplashViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            startActivity<MainActivity>()
            finish()
        }, Constants.SPLASH_DELAY.toLong())
    }
}