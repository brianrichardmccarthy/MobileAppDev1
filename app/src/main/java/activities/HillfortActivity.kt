package activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.brianrichardmccarthy.hillforts.R
import kotlinx.android.synthetic.main.activity_hillfort.*
import models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfortModel = HillfortModel()
    val hillforts = ArrayList<HillfortModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        info("Hillfort Activity started..")

        btnAdd.setOnClickListener {
            hillfortModel.name = hillfortTitle.text.toString()
            hillfortModel.description = hillfortDescription.text.toString()
            if (hillfortModel.name.isNotEmpty()) {
                hillforts.add(hillfortModel.copy())
                hillforts.forEach {
                    info("$it")
                }
            } else {
                toast("Please enter a title")
            }
        }

    }
}
