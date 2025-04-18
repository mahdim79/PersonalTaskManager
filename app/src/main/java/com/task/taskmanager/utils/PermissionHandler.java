package com.task.taskmanager.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import javax.inject.Inject;

import dagger.assisted.AssistedInject;

public class PermissionHandler {

    private Context context;
    @Inject
    public PermissionHandler(Context context){
        this.context = context;
    }

    public Boolean hasNotificationPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}
