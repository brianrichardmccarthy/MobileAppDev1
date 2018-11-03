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

        var edit = false

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfortModel = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
            hillfortTitle.setText(hillfortModel.name)
            hillfortDescription.setText(hillfortModel.description)
            btnAdd.setText(R.string.save_hillfort)
        }

        btnAdd.setOnClickListener {
            hillfortModel.name = hillfortTitle.text.toString()
            hillfortModel.description = hillfortDescription.text.toString()
            if (hillfortModel.name.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {
                    info("$hillfortModel")
                    app.hillforts.update(hillfortModel.copy())
                } else {
                    app.hillforts.create(hillfortModel.copy())
                }
                setResult(AppCompatActivity.RESULT_OK)
                finish()
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
