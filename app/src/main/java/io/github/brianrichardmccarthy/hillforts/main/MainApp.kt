package io.github.brianrichardmccarthy.hillforts.main

import android.app.Application
import io.github.brianrichardmccarthy.hillforts.models.HillfortJsonStore
import io.github.brianrichardmccarthy.hillforts.models.HillfortMemStore
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortJsonStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJsonStore(applicationContext)
        info("Placemark started")
    }
}