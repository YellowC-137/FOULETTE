package com.example.foulette

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.bumptech.glide.Glide.init
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class FouletteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
            instance = this
    }

    companion object {
        lateinit var instance: FouletteApplication
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
    }
}