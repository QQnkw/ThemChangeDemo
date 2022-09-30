package com.them.change

import android.app.Application
import com.them.change.lib.loader.SkinManager

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        SkinManager.getInstance().init(this)
        SkinManager.getInstance().load()
    }
}