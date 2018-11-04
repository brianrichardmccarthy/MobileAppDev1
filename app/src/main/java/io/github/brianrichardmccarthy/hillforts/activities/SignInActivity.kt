package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp
import io.github.brianrichardmccarthy.hillforts.models.UserModel
import kotlinx.android.synthetic.main.activity_sign_in.*
import org.jetbrains.anko.*
import java.security.MessageDigest

class SignInActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app : MainApp
    val CREATE_ACC_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        app = application as MainApp
        toolbarCreate.title = title
        setSupportActionBar(toolbarCreate)

        btnCreateAcc.setOnClickListener {
            // startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
            startActivityForResult(intentFor<CreateAccount>(), CREATE_ACC_REQUEST)
        }

        btnSignIn.setOnClickListener {
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
                val found = app.users.find(user.username)
                if ( found == null) {
                    toast(R.string.error_not_found)
                } else if (found.password != user.password) {
                    toast(R.string.error_logging_in)
                } else {
                    app.users.currentUser = user
                    info("Signed into account $app.users.currentUser")
                    startActivityForResult<HillfortListActivity>(0)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREATE_ACC_REQUEST -> {
                if (app.users.currentUser != null) {
                    startActivityForResult<HillfortListActivity>(0)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_sign_in, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
