package com.task.taskmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.task.core.helper.DataState
import com.task.core.interactors.GetRemoteTasks
import com.task.taskmanager.utils.IsOnline
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    val getRemoteTasks: GetRemoteTasks,
    val isOnline: IsOnline
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        Log.i("SyncWorker","doWork()")
        getRemoteTasks.invoke(isOnline.hasNetworkConnection()).firstOrNull()?.let {
            Log.i("SyncWorker","data received: $it")
            if (it is DataState.Success){
                Log.i("SyncWorker","data received: ${it.value.size}")
            }
        }
        return Result.success()
    }

}