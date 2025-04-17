package com.task.taskmanager.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.task.taskmanager.utils.NotificationHandler;

public class NotifyReceiver extends BroadcastReceiver {

    public static String EXTRA_TASK_NAME = "extra_task_name";
    public static String EXTRA_TASK_ID = "extra_task_id";

    public static String EXTRA_TASK_TIME = "extra_task_time";

    private String TAG = "NotifyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"onReceive");
        String taskName = intent.getStringExtra(EXTRA_TASK_NAME);
        if (taskName != null && !taskName.isEmpty()){
            NotificationHandler notificationHandler = new NotificationHandler(context);
            notificationHandler.showNotificationForTask(taskName);
        }
    }

}
