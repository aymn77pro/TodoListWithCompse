package com.example.todo_list_compose.ui.screenes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_list_compose.TodoListApp
import com.example.todo_list_compose.data.TaskDao
import com.example.todo_list_compose.data.TodoModel
import kotlinx.coroutines.flow.*

class TodoListViewModel(
    private val taskDao: TaskDao
):ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
    private set

    val homeUiState : StateFlow<HomeUiState> =
        taskDao.getAllTask().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HomeUiState()
            )




        // get all task in data base
        fun getAllTask():Flow<List<TodoModel>> = taskDao.getAllTask()



        // add new task in data base
        suspend fun saveItem() {
            if (validateInput()) {
                taskDao.insert(taskUiState.taskDetailse.toTodoModel())

            }
        }

        // update task
        suspend fun update(uiState: TaskDetailse) = taskDao.updateTask(uiState.toTodoModel())



        fun updateUiState(taskDetails: TaskDetailse) {
            taskUiState =
                TaskUiState(taskDetailse = taskDetails, isEntryValid = validateInput(taskDetails))
        }
       // suspend fun updateTask(task: TodoModel) = taskDao.updateTask(task)


    private fun validateInput(uiState: TaskDetailse = TaskUiState().taskDetailse): Boolean {
        return with(uiState) {
            taskName.isNotBlank() && taskDesc.isNotBlank()
        }
    }





    companion object{

        private const val TIMEOUT_MILLIS = 5_000L

    }
}

data class TaskUiState(
    val taskDetailse: TaskDetailse = TaskDetailse(),
    val isEntryValid: Boolean = false
)


data class TaskDetailse(
    val id : Int = 0,
    val taskName:String = "",
    val taskDesc : String = "",
    val taskDone: Boolean = false
)

fun TaskDetailse.toTodoModel():TodoModel = TodoModel(
    idn = id,
    taskName = taskName,
    taskDone = taskDone,
    taskDescription = taskDesc
)

fun TodoModel.toTaskDetailse():TaskDetailse = TaskDetailse(
    id = idn,
    taskName =taskName,
    taskDesc = taskDescription,
    taskDone = taskDone
)

fun TodoModel.toTaskUiState(isEntryValid: Boolean = false):TaskUiState = TaskUiState(
    taskDetailse = this.toTaskDetailse(),
    isEntryValid = isEntryValid
)

data class HomeUiState(val task:List<TodoModel> = listOf() )
