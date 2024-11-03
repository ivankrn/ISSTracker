package ru.ivankrn.isstracker.data.repository

import java.math.BigDecimal

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val PREF_NAME = "SharedPrefs"
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveBigDecimal(key: String, value: BigDecimal) {
        sharedPreferences.edit().putString(key, value.toString()).apply()
    }

    fun getBigDecimal(key: String, defaultValue: BigDecimal): BigDecimal {
        return sharedPreferences.getString(key, defaultValue.toString())?.toBigDecimal() ?: defaultValue
    }

}