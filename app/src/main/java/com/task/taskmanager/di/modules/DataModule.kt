package com.task.taskmanager.di.modules

import android.content.Context
import androidx.room.Room
import com.task.taskmanager.framework.local.TaskDao
import com.task.taskmanager.framework.local.TaskDataBase
import com.task.taskmanager.framework.remote.TaskApiService
import com.task.taskmanager.framework.utils.PrefHandler
import com.task.taskmanager.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideTaskDataBase(@ApplicationContext context: Context): TaskDataBase {
        return Room.databaseBuilder(context, TaskDataBase::class.java, Constants.DB_NAME)
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(appDatabase: TaskDataBase): TaskDao {
        return appDatabase.getTaskDao()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTaskApiService(retrofit: Retrofit): TaskApiService =
        retrofit.create(TaskApiService::class.java)

    @Provides
    @Singleton
    fun providePrefHandler(@ApplicationContext context: Context): PrefHandler = PrefHandler(context)

}