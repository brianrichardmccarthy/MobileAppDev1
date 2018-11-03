package io.github.brianrichardmccarthy.hillforts.main

import android.app.Application
import io.github.brianrichardmccarthy.hillforts.models.HillfortMemStore
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort started from main")
    }
}