package io.github.brianrichardmccarthy.hillforts.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.main.MainApp

class SplashActivity : AppCompatActivity() {
    private lateinit var delayHandler: Handler
    private val SPLASH_DELAY: Long = 3000

    internal val runnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, HillfortListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayHandler = Handler()

        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)

    }

    public override fun onDestroy() {

        if (delayHandler != null) {
            delayHandler.removeCallbacks(runnable)
        }

        super.onDestroy()
    }

}