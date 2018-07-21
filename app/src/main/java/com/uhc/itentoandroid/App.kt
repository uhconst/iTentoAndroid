package com.uhc.itentoandroid

import android.app.Application
import android.content.SharedPreferences

/**
 * Created by const on 7/21/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        prefs = this.getSharedPreferences(PREFS, 0)
    }

    companion object {

        //prefs
        var prefs: SharedPreferences? = null
            private set
        private val PREFS = "PREFS"
    }

}