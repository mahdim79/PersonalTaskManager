package com.task.taskmanager.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.task.core.domain.task.Task;
import com.task.taskmanager.utils.AlarmHandler;
import com.task.taskmanager.utils.NotificationHandler;

public class NotifyReceiver extends BroadcastReceiver {

    public static String EXTRA_TASK = "extra_task";

    private String TAG = "NotifyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"onReceive");
        String taskStr = intent.getStringExtra(EXTRA_TASK);
        if (taskStr != null && !taskStr.isEmpty()){
            Task task = new Gson().fromJson(taskStr,Task.class);
            NotificationHandler notificationHandler = new NotificationHandler(context);
            notificationHandler.showNotificationForTask(task.getTitle());

            AlarmHandler alarmHandler = new AlarmHandler(context);
            alarmHandler.setAlarmForTask(task);
        }
    }

}
