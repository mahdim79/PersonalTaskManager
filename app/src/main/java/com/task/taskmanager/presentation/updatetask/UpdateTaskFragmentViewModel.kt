package com.task.taskmanager.presentation.updatetask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.GetTaskById
import com.task.core.interactors.UpdateTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTaskFragmentViewModel @Inject constructor(private val updateTask: UpdateTask,private val getTaskById: GetTaskById) :
    ViewModel() {

    val updateTaskLiveData = MutableLiveData<DataState<Unit>>()
    val getTaskLiveData = MutableLiveData<DataState<Task>>()

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTask.invoke(task)
                .catch { updateTaskLiveData.postValue(DataState.LocalError(it.message)) }
                .collect {
                    updateTaskLiveData.postValue(it)
                }
        }
    }

    fun getTaskById(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getTaskById.invoke(id)
                .catch { getTaskLiveData.postValue(DataState.LocalError(it.message)) }
                .collect {
                    getTaskLiveData.postValue(it)
                }
        }
    }
}