package com.task.taskmanager.di.modules

import android.content.Context
import android.graphics.Typeface
import com.task.taskmanager.di.annotations.BoldTypeface
import com.task.taskmanager.di.annotations.LightTypeface
import com.task.taskmanager.di.annotations.RegularTypeface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class TypefaceModule {
    @Provides
    @RegularTypeface
    fun provideRegularTypeface(@ApplicationContext context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/yekan_bakh_regular.ttf")
    }

    @Provides
    @BoldTypeface
    fun provideBoldTypeface(@ApplicationContext context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/yekan_bakh_bold.ttf")
    }

    @Provides
    @LightTypeface
    fun provideLightTypeface(@ApplicationContext context: Context): Typeface {
        return Typeface.createFromAsset(context.assets, "fonts/yekan_bakh_light.ttf")
    }
}