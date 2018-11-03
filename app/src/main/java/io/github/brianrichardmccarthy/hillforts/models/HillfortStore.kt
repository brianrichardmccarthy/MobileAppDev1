package io.github.brianrichardmccarthy.hillforts.models

interface HillfortStore {
    fun findAll(): List<HillfortModel>
    fun create(hillfortModel: HillfortModel)
    fun update(hillfortModel: HillfortModel)
}