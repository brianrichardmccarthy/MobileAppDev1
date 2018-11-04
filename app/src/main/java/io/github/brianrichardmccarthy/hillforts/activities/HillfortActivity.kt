package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.helpers.readImage
import io.github.brianrichardmccarthy.hillforts.helpers.readImageFromPath
import io.github.brianrichardmccarthy.hillforts.helpers.showImagePicker
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort.*
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import io.github.brianrichardmccarthy.hillforts.models.Location
import org.jetbrains.anko.*

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfortModel = HillfortModel()
    lateinit var app : MainApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2

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
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfortModel.image))
            if (hillfortModel.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
            btnAdd.setText(R.string.save_hillfort)
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        hillfortLocation.setOnClickListener {
            var location = Location(52.245696, -7.139102, 15f)

            if (hillfortModel.zoom != 0f) {
                location.lat = hillfortModel.lat
                location.lng = hillfortModel.lng
                location.zoom = hillfortModel.zoom
            }

            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfortModel.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_hillfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    hillfortModel.lat = location.lat
                    hillfortModel.lng = location.lng
                    hillfortModel.zoom = location.zoom
                }
            }
        }

    }

}
