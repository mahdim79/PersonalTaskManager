package com.task.taskmanager.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.datastore by preferencesDataStore(name = "settings")