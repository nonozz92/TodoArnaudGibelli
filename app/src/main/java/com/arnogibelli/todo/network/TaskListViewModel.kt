package com.arnogibelli.todo.network

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnogibelli.todo.tasklist.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskListViewModel : ViewModel() {
    private val webService = Api.taskWebService

    // privée mais modifiable à l'intérieur du VM:
    private val _tasksStateFlow = MutableStateFlow<List<Task>>(emptyList())
    // même donnée mais publique et non-modifiable à l'extérieur afin de pouvoir seulement s'y abonner:
    public val tasksStateFlow: StateFlow<List<Task>> = _tasksStateFlow.asStateFlow()

    fun refresh() {
        viewModelScope.launch {
            val response = webService.getTasks() // Call HTTP (opération longue)
            if (!response.isSuccessful) { // à cette ligne, on a reçu la réponse de l'API
                Log.e("Network", "Error: ${response.errorBody()}")
            }
            val fetchedTasks = response.body()!!
            _tasksStateFlow.value = fetchedTasks // on modifie le flow, ce qui déclenche ses observers
        }
    }

    // à compléter plus tard:
    fun create(task: Task) {
        viewModelScope.launch {
            val response = webService.create(task)
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.errorBody()}")
                return@launch
            }
            val createTask = response.body()!!
            _tasksStateFlow.value = _tasksStateFlow.value + createTask
        }
    }
    fun update(task: Task) {
        viewModelScope.launch {
            val response = webService.update(task, task.id) // TODO: appel réseau
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch
            }

            val updatedTask = response.body()!!
            _tasksStateFlow.value = _tasksStateFlow.value - task + updatedTask
        }
    }
    fun delete(task: Task) {
        viewModelScope.launch {
            val response = webService.delete(task.id)// TODO: appel réseau
            if (!response.isSuccessful) {
                Log.e("Network", "Error: ${response.raw()}")
                return@launch

            }

            _tasksStateFlow.value = _tasksStateFlow.value - task
        }
    }
}