package com.android.albumlist.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutId: Int
    protected abstract fun initializeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        initializeViewModel()
        observeViewModel()
    }

    abstract fun observeViewModel()

}