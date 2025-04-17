package com.task.taskmanager.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.task.core.domain.task.Task;
import com.task.taskmanager.receivers.NotifyReceiver;

import java.util.Calendar;

public class AlarmHandler {
    private AlarmManager alarmManager;
    private Context context;

    private String TAG = "AlarmHandler";

    private final Long DAILY_MILLIS = 86400000L;

    public AlarmHandler(Context context){
        this.context = context;
        this.alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    private PendingIntent createPendingIntentForTask(Task task){
        Intent intent = new Intent(context, NotifyReceiver.class);
        intent.putExtra(NotifyReceiver.EXTRA_TASK_NAME,task.getTitle());
        intent.putExtra(NotifyReceiver.EXTRA_TASK_ID,task.getId());

        int requestCode = 0;
        if (task.getId() != null)
            requestCode = task.getId();
        return PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_MUTABLE);
    }

    public void setAlarmForTask(Task task){
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,task.getTime(),DAILY_MILLIS,createPendingIntentForTask(task));
        Log.i(TAG,"setAlarmForTask");
    }

    public void cancelTaskAlarm(Task task){
        alarmManager.cancel(createPendingIntentForTask(task));
        Log.i(TAG,"cancelTaskAlarm");
    }

}
