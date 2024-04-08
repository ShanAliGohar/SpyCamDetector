package com.example.spycamdetector.sharedPref

import android.content.Context
import android.content.SharedPreferences

class SaveData (context : Context) {
    var sharedPreferences : SharedPreferences = context.getSharedPreferences("file",Context.MODE_PRIVATE)

    fun setDarkMoodState (state : Boolean?){
        val editor = sharedPreferences.edit()
        editor.putBoolean("Dark",state!!)
        editor.apply()
    }

    fun loadDarkModeState(): Boolean {
        return sharedPreferences.getBoolean("Dark", false)
    }
}