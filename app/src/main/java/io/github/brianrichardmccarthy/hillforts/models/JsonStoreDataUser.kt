package io.github.brianrichardmccarthy.hillforts.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.brianrichardmccarthy.hillforts.helpers.exists
import io.github.brianrichardmccarthy.hillforts.helpers.read
import io.github.brianrichardmccarthy.hillforts.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.ArrayList

val JSON_USER_FILE = "users.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<ArrayList<UserModel>>() {}.type

class JsonStoreDataUser : AnkoLogger {

    val context: Context
    private var users = mutableListOf<UserModel>()
    var currentUser = UserModel()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_USER_FILE)) {
            deserialize()
        }
    }

    private fun serialize() {
        val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
        write(context, JSON_USER_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_USER_FILE)
        users = Gson().fromJson(jsonString, listTypeUser)
    }

    fun find(username: String): UserModel? {
        return users.find { p -> p.username == username }
    }

    fun create(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    fun update(user: UserModel) {
        var found: UserModel? = users.find { p -> p.id == user.id }
        if (found != null) {
            found.username = user.username
            found.password = user.password
            serialize()
        }
    }

    fun delete(user: UserModel) {
        users.remove(user)
        serialize()
    }

}