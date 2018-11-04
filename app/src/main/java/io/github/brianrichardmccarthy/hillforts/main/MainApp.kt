package io.github.brianrichardmccarthy.hillforts.main

import android.app.Application
import io.github.brianrichardmccarthy.hillforts.models.JsonStoreData
import io.github.brianrichardmccarthy.hillforts.models.JsonStoreDataUser
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: JsonStoreData
    lateinit var users: JsonStoreDataUser

    override fun onCreate() {
        super.onCreate()
        hillforts = JsonStoreData(applicationContext)
        users = JsonStoreDataUser(applicationContext)
        info("Placemark started")
    }
}