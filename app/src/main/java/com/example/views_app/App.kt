package com.example.views_app

import android.app.Application

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        PrefUtils.init(this)
    }
}