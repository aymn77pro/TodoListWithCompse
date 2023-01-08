package com.example.todo_list_compose.ui.screenes.detiles

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.nav.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.TodoListViewModel
import com.example.todo_list_compose.ui.theme.TODO_LIST_COMPOSETheme
import kotlinx.coroutines.launch

object NewTaskDestination: NavigationDestination {
    override val route: String = "new_task"
    override val titleRes: Int = R.string.addNewTask
}


@Composable
fun NewTaskApp(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel(factory = AppViewModelProvider.Factory),

    ){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar ={
            TodoListTopAppBar(
                title = stringResource(id = NewTaskDestination.titleRes) ,
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp)
        }
    ) { paddingValues ->
        InputTask(
            todoModel = viewModel.taskUiState.taskDetailse,
            onItemValueChange = viewModel::updateUiState,
            onClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    navigateBack()
                }
            },
            modifier = modifier.padding(paddingValues)
        )

    }



//

}



@Composable
fun InputTask(
    todoModel: TaskDetailse,
    modifier: Modifier = Modifier,
    onClick : () -> Unit ,
    onItemValueChange:(TaskDetailse) -> Unit
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        InputTaskTextFiled(todoModel, onItemValueChange =  onItemValueChange)
        Spacer(modifier = modifier.padding(16.dp))
        Button(onClick = onClick, modifier = modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.saveTask))
        }
    }
}

@Composable
fun InputTaskTextFiled(
    todoModel: TaskDetailse,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onItemValueChange:(TaskDetailse) -> Unit
){
    OutlinedTextField(
        value = todoModel.taskName ,
        onValueChange ={
            onItemValueChange(TaskDetailse(taskName = it))
        },
        label = { Text(text = stringResource(id = R.string.taskName))},
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
    Spacer(modifier = modifier.padding(16.dp))
    OutlinedTextField(
        value = todoModel.taskDesc ,
        onValueChange ={
            onItemValueChange(TaskDetailse(taskDesc = it))
        },
        label = { Text(text = stringResource(id = R.string.taskDesc))},
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    )
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview(){
    TODO_LIST_COMPOSETheme {


    }
}