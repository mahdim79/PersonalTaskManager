package com.task.taskmanager

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.task.taskmanager.utils.Constants
import com.task.taskmanager.utils.SettingManager
import com.task.taskmanager.worker.SyncWorker
import com.task.taskmanager.worker.SyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class TaskApplication : Application(), Configuration.Provider {

    companion object {
        const val SYNC_WORKER_NAME = "sync_worker"
    }

    @Inject
    lateinit var syncWorkerFactory: SyncWorkerFactory

    @Inject
    lateinit var settingManager: SettingManager

    var isInitialDarkModeEnable = false

    override fun onCreate() {
        super.onCreate()
        initAppCenter()
        configureAppTheme()
        startSyncWorker()
    }

    private fun initAppCenter() {
        AppCenter.start(this, Constants.APP_CENTER_DSN, Analytics::class.java, Crashes::class.java)
    }

    private fun startSyncWorker() {
        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(15, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(SYNC_WORKER_NAME, ExistingPeriodicWorkPolicy.KEEP, request)
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

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(syncWorkerFactory).build()
}