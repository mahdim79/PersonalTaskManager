package com.task.taskmanager.presentation.addtask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.AddTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskFragmentViewModel @Inject constructor(private val addTask: AddTask) : ViewModel() {

    val addTaskLiveData = MutableLiveData<DataState<Unit>>()

    fun addNewTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            addTask.invoke(task)
                .catch { addTaskLiveData.postValue(DataState.LocalError(it.message)) }
                .collect{
                    addTaskLiveData.postValue(it)
                }
        }
    }
}