package com.task.taskmanager.utils

import com.task.core.domain.task.Task

class MockData {
    companion object {
        fun getMockTasks():List<Task>{
            return arrayListOf<Task>().apply {
                add(Task(0,"task","description",1744814582111))
                add(Task(1,"task","description",1744814582111))
                add(Task(2,"task","description",1744814582111))
                add(Task(3,"task","description",1744814582111))
                add(Task(4,"task","description",1744814582111))
                add(Task(5,"task","description",1744814582111))
            }
        }
    }

}