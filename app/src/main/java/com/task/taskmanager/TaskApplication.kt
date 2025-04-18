package com.task.taskmanager

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.task.taskmanager.utils.SettingManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class TaskApplication:Application() {

    @Inject
    lateinit var settingManager: SettingManager

    var isInitialDarkModeEnable = false

    override fun onCreate() {
        super.onCreate()
        configureAppTheme()
    }

    private fun configureAppTheme() {
        runBlocking {
            if (settingManager.getDarkMode()) {
                isInitialDarkModeEnable = true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}