package com.task.taskmanager.di.modules

import android.content.Context
import com.task.taskmanager.utils.AlarmHandler
import com.task.taskmanager.utils.IsOnline
import com.task.taskmanager.utils.NotificationHandler
import com.task.taskmanager.utils.PermissionHandler
import com.task.taskmanager.utils.SettingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @Singleton
    fun providePermissionHandler(@ApplicationContext context: Context):PermissionHandler{
        return PermissionHandler(context)
    }

    @Provides
    @Singleton
    fun provideNotificationHandler(@ApplicationContext context: Context):NotificationHandler{
        return NotificationHandler(context)
    }

    @Provides
    @Singleton
    fun provideAlarmHandler(@ApplicationContext context: Context):AlarmHandler{
        return AlarmHandler(context)
    }

    @Provides
    @Singleton
    fun provideSettingManager(@ApplicationContext context: Context):SettingManager{
        return SettingManager(context)
    }

    @Provides
    @Singleton
    fun provideIsOnline(@ApplicationContext context: Context):IsOnline{
        return IsOnline(context)
    }
}