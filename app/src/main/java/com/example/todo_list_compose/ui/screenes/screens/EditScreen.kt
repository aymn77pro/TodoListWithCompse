package com.example.todo_list_compose.ui.screenes.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.navigation.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.detiles.InputTask
import com.example.todo_list_compose.ui.screenes.viewModel.EditTaskViewModel
import kotlinx.coroutines.launch

object EditDestination : NavigationDestination {
    override val route: String = "Edit_Task"
    override val titleRes: Int = R.string.editTask
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}

@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = viewModel(factory = AppViewModelProvider.Factory )
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar ={
           TodoListTopAppBar(
               title = stringResource(id = EditDestination.titleRes),
               canNavigateBack = true,
               navigateUp = onNavigateUp )
        }) {paddingValues ->
            InputTask(
                todoModel = viewModel.uiEditState ,
                onClick = {
                          coroutineScope.launch {
                              viewModel.updateTask()
                              navigateBack()
                          }
                },
                onItemValueChange = viewModel::updateUiState,
                modifier = modifier.padding(paddingValues),
                text = R.string.editTask
            )
        Log.e("TAG Edit", "EditScreen: ${viewModel.uiEditState} ", )
    }
}