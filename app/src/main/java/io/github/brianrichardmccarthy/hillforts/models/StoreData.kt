package io.github.brianrichardmccarthy.hillforts.models

interface StoreData<T> {
    fun findAll(): List<T>
    fun create(hillfortModel: T)
    fun update(hillfortModel: T)
    fun delete(hillfortModel: T)
}