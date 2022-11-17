package com.andreyyurko.hhtinder.singleton

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharedPreferencesSingleton {
    var context: Context? = null

    companion object {
        val instance = SharedPreferencesSingleton()
    }

    fun setSingletonContext(context: Context) {
        this.context = context
    }

    fun getSharedPreferences(): SharedPreferences? {
        if (this.context != null) {
            return PreferenceManager.getDefaultSharedPreferences(this.context)
        } else {
            return null
        }
    }
}