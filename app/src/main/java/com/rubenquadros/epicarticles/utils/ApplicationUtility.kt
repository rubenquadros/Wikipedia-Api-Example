package com.rubenquadros.epicarticles.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.util.*

class ApplicationUtility {

    companion object {

        fun showSnack(msg: String, view: View, action: String){
            val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
            snackBar.setAction(action) {
                snackBar.dismiss()
            }
            snackBar.show()
        }

        fun showDialog(context: Context, title: String, message: String, button: String) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(button){ _, _ ->
                // do nothing
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        fun getRandomTopic(): String {
            val index = getRandomNumber()
            return ApplicationConstants.ARTICLE_LIST[index]
        }

        private fun getRandomNumber(): Int {
            val random = Random()
            val low = 0
            val high = ApplicationConstants.ARTICLE_LIST.size
            return random.nextInt(high-low) + low
        }

        fun getStringForUrl(input: String): String {
            return if(input.contains(" ")) {
                input.replace("\\s".toRegex(), "_")
            }else {
                input
            }
        }
    }
}