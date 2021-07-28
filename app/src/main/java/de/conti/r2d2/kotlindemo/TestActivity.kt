package de.conti.r2d2.kotlindemo

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.test_layout)

        showToastMessage("custom method on activity")
        findViewById<Button>(R.id.button).getVisibility1()

    }

    override fun onRestart() {
        super.onRestart()
    }
}