package com.task.taskmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.AddMultipleTasks
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.GetRemoteTasks
import com.task.taskmanager.utils.AlarmHandler
import com.task.taskmanager.utils.DataSyncManager
import com.task.taskmanager.utils.IsOnline
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val dataSyncManager: DataSyncManager
) : CoroutineWorker(context, workerParameters) {

    private val TAG = "SyncWorker"

    override suspend fun doWork(): Result {
        Log.i(TAG,"doWork()")
        dataSyncManager.startSyncOperation()
        return Result.success()
    }

}