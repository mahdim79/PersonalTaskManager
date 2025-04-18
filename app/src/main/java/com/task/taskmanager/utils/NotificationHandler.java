package com.task.taskmanager.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.task.taskmanager.R;
import com.task.taskmanager.presentation.mainactivity.MainActivity;

import javax.inject.Inject;

import dagger.assisted.AssistedInject;
import dagger.hilt.android.AndroidEntryPoint;

public class NotificationHandler {

    private Context context;
    private NotificationManager notificationManager;
    PermissionHandler permissionHandler;
    @Inject
    public NotificationHandler(Context context){
        this.context = context;
        this.permissionHandler = new PermissionHandler(context);
        notificationManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }
    public void showNotificationForTask(String taskName){
        if (!permissionHandler.hasNotificationPermission())
            return;

        Notification notification = new NotificationCompat.Builder(context,createNotificationChannel())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setOngoing(false)
                .setAutoCancel(true)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.notificationContent,taskName))
                .setContentIntent(PendingIntent.getActivity(context,1001,new Intent(context, MainActivity.class),PendingIntent.FLAG_IMMUTABLE))
                .build();
        notificationManager.notify(5001,notification);
    }

    private String createNotificationChannel(){
        String channelId = "task_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channelId,"task_manager_channel",NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        return channelId;
    }

}
