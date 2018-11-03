package io.github.brianrichardmccarthy.hillforts.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort.*
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfortModel = HillfortModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        info("Hillfort Activity started..")
        app = application as MainApp

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        btnAdd.setOnClickListener {
            hillfortModel.name = hillfortTitle.text.toString()
            hillfortModel.description = hillfortDescription.text.toString()
            if (hillfortModel.name.isNotEmpty()) {
                app.hillforts.add(hillfortModel.copy())
                app.hillforts.forEach {
                    info("$it")
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            } else {
                toast("Please enter a title")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
