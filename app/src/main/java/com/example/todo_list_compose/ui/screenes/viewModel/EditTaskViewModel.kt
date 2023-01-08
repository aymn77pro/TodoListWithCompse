package com.example.todo_list_compose.ui.screenes.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo_list_compose.data.TaskDao
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TaskUiState
import com.example.todo_list_compose.ui.screenes.screens.EditDestination
import com.example.todo_list_compose.ui.screenes.toTaskUiState
import com.example.todo_list_compose.ui.screenes.toTodoModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskDao: TaskDao
):ViewModel() {


    var uiEditState by mutableStateOf(TaskUiState())
    private set

    private val taskId:Int = checkNotNull(savedStateHandle[EditDestination.taskIdArg])

    init {
        viewModelScope.launch {
           uiEditState = taskDao.getOneTask(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState(isEntryValid = true)
        }
    }

    suspend fun updateTask(){
        if (validateInput(uiEditState.taskDetailse)){
            taskDao.updateTask(uiEditState.taskDetailse.toTodoModel())
        }
    }

    fun updateUiState(taskDetailse: TaskDetailse){
        uiEditState = TaskUiState(
            taskDetailse = taskDetailse,
            isEntryValid = validateInput(taskDetailse) )
    }



    private fun validateInput(uiState: TaskDetailse = uiEditState.taskDetailse ): Boolean {
        return with(uiState) {
           taskName.isNotBlank() && taskDesc.isNotBlank()
        }
    }
}