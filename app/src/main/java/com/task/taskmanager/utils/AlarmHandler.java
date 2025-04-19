package com.task.taskmanager.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.task.core.domain.task.Task;
import com.task.core.interactors.GetLocalTasks;
import com.task.taskmanager.receivers.NotifyReceiver;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

public class AlarmHandler {
    private AlarmManager alarmManager;
    private Context context;

    private String TAG = "AlarmHandler";

    @Inject
    public AlarmHandler(Context context){
        this.context = context;
        this.alarmManager = ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE));
    }

    private PendingIntent createPendingIntentForTask(Task task){
        Intent intent = new Intent(context, NotifyReceiver.class);

        String taskStr = new Gson().toJson(task,Task.class);
        intent.putExtra(NotifyReceiver.EXTRA_TASK,taskStr);

        int requestCode = 0;
        if (task.getId() != null)
            requestCode = task.getId();
        return PendingIntent.getBroadcast(context,requestCode,intent,PendingIntent.FLAG_MUTABLE);
    }

    public void setAlarmForTask(Task task){
        long validatedTime = getValidatedTime(task.getTime());

        cancelTaskAlarm(task);
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,validatedTime,createPendingIntentForTask(task));
    }

    public void cancelTaskAlarm(Task task){
        alarmManager.cancel(createPendingIntentForTask(task));
    }


    private long getValidatedTime(Long time){
        Calendar taskCalendar = Calendar.getInstance();
        taskCalendar.setTimeInMillis(time);

        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTimeInMillis(System.currentTimeMillis());

        currentCalendar.set(Calendar.HOUR_OF_DAY,taskCalendar.get(Calendar.HOUR_OF_DAY));
        currentCalendar.set(Calendar.MINUTE,taskCalendar.get(Calendar.MINUTE));
        currentCalendar.set(Calendar.SECOND,0);
        currentCalendar.set(Calendar.MILLISECOND,0);

        if (currentCalendar.before(Calendar.getInstance()))
            currentCalendar.add(Calendar.DAY_OF_MONTH,1);

        return currentCalendar.getTimeInMillis();
    }

}
