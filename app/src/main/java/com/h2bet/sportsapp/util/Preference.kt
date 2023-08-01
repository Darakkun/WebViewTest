package com.h2bet.sportsapp.util

import android.content.Context
import android.content.SharedPreferences

object Preference {
    val kuki = "COOKI"

    fun getPrefeence(context: Context): SharedPreferences = context.getSharedPreferences("bykmeker", Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.cokies
        get() = getString(kuki, "xyko")
        set(value) {
            editMe {
                it.putString(kuki, value)
            }
        }

}