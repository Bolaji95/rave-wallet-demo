package com.bolaandroid.ravewalletdemo.Context
import android.app.Application

/**
 * Created by Owner on 10/4/2018.
 */
import android.content.Context

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object {
        private var mInstance: App? = null

        fun context(): Context {
            return mInstance!!.applicationContext
        }
    }

}