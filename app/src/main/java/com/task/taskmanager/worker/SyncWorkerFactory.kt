package com.task.taskmanager.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.task.core.interactors.GetRemoteTasks
import com.task.taskmanager.utils.IsOnline
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor() :WorkerFactory() {

    @Inject
    lateinit var getRemoteTasks: GetRemoteTasks

    @Inject
    lateinit var isOnline:IsOnline

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return SyncWorker(appContext,workerParameters,getRemoteTasks,isOnline)
    }
}