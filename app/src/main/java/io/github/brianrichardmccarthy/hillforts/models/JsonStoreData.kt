package io.github.brianrichardmccarthy.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.brianrichardmccarthy.hillforts.helpers.exists
import io.github.brianrichardmccarthy.hillforts.helpers.read
import io.github.brianrichardmccarthy.hillforts.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*


val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class JsonStoreData : StoreData<HillfortModel>, AnkoLogger {

    val context: Context
    var hillforts = mutableListOf<HillfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
    }

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }

    override fun create(hillfortModel: HillfortModel) {
        hillfortModel.id = generateRandomId()
        hillforts.add(hillfortModel)
        serialize()
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
            serialize()
        }
    }

    override fun delete(hillfortModel: HillfortModel) {
        hillforts.remove(hillfortModel)
        serialize()
    }

}