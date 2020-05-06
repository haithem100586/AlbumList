package com.android.albumlist

class AppTest: App() {
    override fun initDagger() {
        DaggerTestAppComponent.builder().application(this).build().inject(this)
    }
}git