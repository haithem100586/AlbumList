package com.android.albumlist

import com.android.albumlist.framework.di.DaggerTestAppComponent

class AppTest: App() {
    override fun initDagger() {
        DaggerTestAppComponent.builder().application(this).build().inject(this)
    }
}