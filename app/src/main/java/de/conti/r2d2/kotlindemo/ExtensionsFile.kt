package de.conti.r2d2.kotlindemo

import android.app.Activity
import android.app.Service
import android.view.View
import android.widget.Button
import android.widget.Toast

fun Activity.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Button.showToastOnButtonClick(message: String, context: Activity) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun View.getVisibility1(): Int {
    if (this.getVisibility() == View.VISIBLE) return 1
    else return 0
}
 