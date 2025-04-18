package com.task.taskmanager.presentation.updatetask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.GetTaskById
import com.task.core.interactors.UpdateTask
import com.task.taskmanager.utils.AlarmHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTaskFragmentViewModel @Inject constructor(
    private val updateTask: UpdateTask,
    private val getTaskById: GetTaskById,
    private val alarmHandler: AlarmHandler
) :
    ViewModel() {

    val updateTaskLiveData = MutableLiveData<DataState<Task>>()
    val getTaskLiveData = MutableLiveData<DataState<Task>>()

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTask.invoke(task)
                .catch { updateTaskLiveData.postValue(DataState.LocalError(it.message)) }
                .collect {
                    if (it is DataState.Success)
                        updateTaskLiveData.postValue(DataState.Success(task))
                    else
                        updateTaskLiveData.postValue(DataState.LocalError())
                }
        }
    }

    fun getTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTaskById.invoke(id)
                .catch { getTaskLiveData.postValue(DataState.LocalError(it.message)) }
                .collect {
                    getTaskLiveData.postValue(it)
                }
        }
    }

    fun updateTaskTime(task: Task){
        alarmHandler.updateTaskTime(task)
    }

}