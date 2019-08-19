package net.nshiba.offlinesupportsample.utils

import android.app.Application
import timber.log.Timber

class OfflineSupportApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}