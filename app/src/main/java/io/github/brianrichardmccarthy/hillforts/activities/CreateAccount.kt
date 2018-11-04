package io.github.brianrichardmccarthy.hillforts.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.security.MessageDigest

class CreateAccount : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        app = application as MainApp
        toolbarCreate.title = title
        setSupportActionBar(toolbarCreate)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnCreateIn.setOnClickListener {
            var user = UserModel()
            user.username = username.text.toString()
            user.password = userpassword.text.toString()
            if (user.username.isNullOrEmpty()) {
                toast(R.string.error_user)
            } else if (user.password.isNullOrEmpty()) {
                toast(R.string.error_password)
            } else {
                val bytes = user.password.toByteArray()
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(bytes)
                user.password = digest.fold("") { str, it -> str + "%02x".format(it) }
                app.users.create(user.copy())
                app.users.currentUser = user.copy()
                info("Created and Signed into account $app.users.currentUser")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_create, menu)
        return super.onCreateOptionsMenu(menu)
    }

}
