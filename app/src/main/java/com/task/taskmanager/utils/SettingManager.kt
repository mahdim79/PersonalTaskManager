package com.task.taskmanager.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingManager @Inject constructor(private val context: Context) {

    companion object {
        val KEY_DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    suspend fun getDarkMode():Boolean{
        return context.datastore.data
            .map { preferences ->
                preferences[KEY_DARK_MODE] ?: false
            }.first()
    }

    suspend fun setDarkMode(enabled:Boolean){
        context.datastore.edit {
            it[KEY_DARK_MODE] = enabled
        }
    }
}