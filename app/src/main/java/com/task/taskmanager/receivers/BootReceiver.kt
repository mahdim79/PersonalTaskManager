package com.task.taskmanager.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.task.core.helper.DataState
import com.task.core.interactors.GetLocalTasks
import com.task.taskmanager.utils.AlarmHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver:BroadcastReceiver() {

    @Inject
    lateinit var getLocalTasks: GetLocalTasks

    @Inject
    lateinit var alarmHandler: AlarmHandler

    override fun onReceive(context: Context?, intent: Intent?) {
        resetAllAlarms()
    }

    private fun resetAllAlarms() {
        CoroutineScope(Dispatchers.IO).launch {
            getLocalTasks.invoke()
                .collect {
                    if (it is DataState.Success){
                        it.value.forEach { task ->
                            alarmHandler.setAlarmForTask(task)
                        }
                    }
                }
        }
    }
}