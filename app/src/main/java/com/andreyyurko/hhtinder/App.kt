package com.andreyyurko.hhtinder

import android.app.Application
import android.os.StrictMode
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {

        //TODO: Вырезать это. Я не знаю как в котлине создавать корутины
        StrictMode
            .setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())
        super.onCreate()
    }
}