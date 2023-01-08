package com.example.todo_list_compose.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo_list_compose.TodoListApp
import com.example.todo_list_compose.ui.screenes.TodoListViewModel
import com.example.todo_list_compose.ui.screenes.viewModel.DeleteTaskViewModel
import com.example.todo_list_compose.ui.screenes.viewModel.EditTaskViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            TodoListViewModel(
                taskDao = TodoListApp().database.taskDao()
            )
        }
        initializer {
            DeleteTaskViewModel(
                taskDao = TodoListApp().database.taskDao(),
                savedStateHandle = this.createSavedStateHandle()
            )
        }
        initializer {
            EditTaskViewModel(
                taskDao = TodoListApp().database.taskDao(),
                savedStateHandle = this.createSavedStateHandle()
            )
        }
    }
}


fun CreationExtras.TodoListApp(): TodoListApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TodoListApp)