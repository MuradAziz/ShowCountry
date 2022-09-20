package com.example.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomShared {

    companion object{
        private val PREFENCE_TIME="prefenceTime"
        private var sharedPreferences:SharedPreferences?=null

        @Volatile private var instance:CustomShared?=null
        private val lock=Any()
        operator fun invoke(context: Context): CustomShared= instance?: synchronized(lock){
            instance?: makeCustomShared(context).also {
                instance=it
            }
        }

        private fun makeCustomShared(context: Context):CustomShared{
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context)
            return CustomShared()
        }
    }
    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFENCE_TIME, time)
        }
    }
    fun getTime()= sharedPreferences?.getLong(PREFENCE_TIME, 0)
}