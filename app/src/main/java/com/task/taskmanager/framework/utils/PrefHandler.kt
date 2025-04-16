package com.task.taskmanager.framework.utils

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefHandler @Inject constructor(@ApplicationContext val context: Context) {
    private val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREF_NAME = "task_manager_pref"
        const val KEY_DARK_MODE = "DARK_MODE_ENABLED"
    }

    fun setDarkMode(enabled: Boolean) {
        setPreference(KEY_DARK_MODE, enabled)
    }

    fun getDarkMode(): Boolean = getBoolean(KEY_DARK_MODE, false)

    private fun setPreference(key: String?, value: Any?) {
        sharedPref.edit() {
            when (value) {
                is Int -> putInt(
                    key,
                    (value as Int?)!!
                )

                is String -> putString(
                    key,
                    value as String?
                )

                is Boolean -> putBoolean(
                    key,
                    (value as Boolean?)!!
                )

                is Long -> putLong(
                    key,
                    (value as Long?)!!
                )

                is Set<*> -> putStringSet(
                    key,
                    value as Set<String?>?
                )
            }
        }
    }

    private fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPref.getInt(key, defaultValue)
    }

    private fun getString(key: String?, defaultValue: String?): String? {
        return sharedPref.getString(key, defaultValue)
    }

    private fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    private fun getLong(key: String?, defaultValue: Long): Long {
        return sharedPref.getLong(key, defaultValue)
    }

    private fun getStringSet(
        key: String?,
        defaultValue: Set<String?>?
    ): Set<String?>? {
        return sharedPref.getStringSet(key, defaultValue)
    }

    private fun clearTag(keyName: String?) {
        sharedPref.edit {
            remove(keyName)
        }
    }

    private fun clear(): Boolean {
        return sharedPref.edit().clear().commit()
    }

    private fun contain(key: String?): Boolean {
        return sharedPref.contains(key)
    }

    private fun removeSinglePreference(key: String?) {
        sharedPref.edit { remove(key) }
    }

}