package com.task.taskmanager.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.task.core.domain.task.Task;
import com.task.taskmanager.utils.AlarmHandler;
import com.task.taskmanager.utils.IsOnline;
import com.task.taskmanager.utils.NotificationHandler;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotifyReceiver extends BroadcastReceiver {

    public static String EXTRA_TASK = "extra_task";

    private String TAG = "NotifyReceiver";

    @Inject
    AlarmHandler alarmHandler;

    @Inject
    NotificationHandler notificationHandler;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"onReceive");
        String taskStr = intent.getStringExtra(EXTRA_TASK);
        if (taskStr != null && !taskStr.isEmpty()){
            Task task = new Gson().fromJson(taskStr,Task.class);
            notificationHandler.showNotificationForTask(task.getTitle());
            alarmHandler.setAlarmForTask(task);
        }
    }

}
