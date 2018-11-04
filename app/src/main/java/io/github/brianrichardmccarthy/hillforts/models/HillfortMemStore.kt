package io.github.brianrichardmccarthy.hillforts.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId: Long = 0

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {
    val hillforts = ArrayList<HillfortModel>()

    override fun create(hillfortModel: HillfortModel) {
        hillfortModel.id = getId()
        hillforts.add(hillfortModel)
        logAll()
    }

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun update(hillfortModel: HillfortModel) {
        var found: HillfortModel? = hillforts.find { p -> p.id == hillfortModel.id }
        if (found != null) {
            found.name = hillfortModel.name
            found.description = hillfortModel.description
            found.image = hillfortModel.image
            found.lat = hillfortModel.lat
            found.lng = hillfortModel.lng
            found.zoom = hillfortModel.zoom
            logAll()
        }
    }

    fun logAll() {
        hillforts.forEach {
            info("$it")
        }
    }

}