package com.h2bet.sportsapp

import android.content.Context
import android.content.SharedPreferences

object Preference {
    val COOKIESKEY = "COOKI"

    fun getprefer(context: Context): SharedPreferences = context.getSharedPreferences("bykmeker", Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.cokies
        get() = getString(COOKIESKEY, "xyko")
        set(value) {
            editMe {
                it.putString(COOKIESKEY, value)
            }
        }

    var SharedPreferences.fortuneruri
        get() = getString("URL", "")
        set(value) {
            editMe {
                it.putString("URL", value)
            }
        }
}