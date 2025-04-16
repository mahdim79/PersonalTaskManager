package com.task.taskmanager.framework.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("title")
    @Expose
    val title:String,
    @SerializedName("description")
    @Expose
    val description:String,
    @SerializedName("time")
    @Expose
    val time:Long,
)