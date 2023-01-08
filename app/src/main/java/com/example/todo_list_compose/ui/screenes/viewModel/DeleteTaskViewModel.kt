package com.example.todo_list_compose.ui.screenes.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_list_compose.TodoListApp
import com.example.todo_list_compose.data.TaskDao
import com.example.todo_list_compose.data.TodoModel
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TodoListViewModel
import com.example.todo_list_compose.ui.screenes.screens.DetilesDestination
import com.example.todo_list_compose.ui.screenes.toTaskDetailse
import com.example.todo_list_compose.ui.screenes.toTodoModel
import kotlinx.coroutines.flow.*

class DeleteTaskViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskDao: TaskDao
):ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[DetilesDestination.taskIdArg])

    val uiStat : StateFlow<TaskDetailse> =
        taskDao.getOneTask(itemId).filterNotNull().map {
            it.toTaskDetailse()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskDetailse()
        )

    // delete task from data base
    suspend fun deleteTask() = taskDao.deleteTask(uiStat.value.toTodoModel())





    companion object {

            private const val TIMEOUT_MILLIS = 5_000L
                  }
}
