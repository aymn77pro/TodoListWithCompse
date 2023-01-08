package com.example.todo_list_compose.ui.screenes.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.nav.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.detiles.InputTask
import com.example.todo_list_compose.ui.screenes.detiles.InputTaskTextFiled
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
                todoModel = viewModel.uiEditState.taskDetailse ,
                onClick = {
                          coroutineScope.launch {
                              viewModel.updateTask()
                              navigateBack()
                          }
                },
                onItemValueChange = viewModel::updateUiState,
                modifier = modifier.padding(paddingValues)
            )
    }
}

@Composable
fun EditScreenBody(

){}