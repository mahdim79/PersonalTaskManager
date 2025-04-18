package com.task.taskmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
):CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result {
        Log.i("SyncWorker","doWork()")
        return Result.success()
    }
}