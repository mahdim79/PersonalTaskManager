package com.task.taskmanager.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.core.domain.task.Task
import com.task.core.helper.DataState
import com.task.core.interactors.GetLocalTasks
import com.task.core.interactors.RemoveTask
import com.task.taskmanager.utils.AlarmHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val getLocalTasks: GetLocalTasks,private val removeTask: RemoveTask): ViewModel() {

    val localTasksLiveData = MutableLiveData<DataState<List<Task>>>()
    val removeTaskLiveData = MutableLiveData<DataState<Task>>()

    fun getLocalTasks(){
        viewModelScope.launch(Dispatchers.IO) {
            getLocalTasks.invoke()
                .catch { localTasksLiveData.postValue(DataState.LocalError(it.message)) }
                .collect {
                    localTasksLiveData.postValue(it)
                }
        }
    }

    fun removeTask(task: Task){
        task.id?.let { tId ->
            viewModelScope.launch(Dispatchers.IO) {
                removeTask.invoke(tId)
                    .catch { removeTaskLiveData.postValue(DataState.LocalError(it.message)) }
                    .collect {
                        if (it is DataState.Success)
                           removeTaskLiveData.postValue(DataState.Success(task))
                        else
                            removeTaskLiveData.postValue(DataState.LocalError())
                    }
            }
        }
    }
}