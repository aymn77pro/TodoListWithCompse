package com.example.todo_list_compose.ui.screenes.detiles

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.navigation.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TaskUiState
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.TodoListViewModel
import com.example.todo_list_compose.ui.theme.TODO_LIST_COMPOSETheme
import kotlinx.coroutines.launch

object NewTaskDestination: NavigationDestination {
    override val route: String = "new_task"
    override val titleRes: Int = R.string.addNewTask
}


@Composable
fun NewTask(
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
                navigateUp = onNavigateUp
            )
        }
    ) { paddingValues ->
        InputTask(
            todoModel = viewModel.taskUiState,
            onItemValueChange = viewModel::updateUiState,
            onClick = {
                coroutineScope.launch {
                    viewModel.saveItem()
                    //navigateBack()
                    Log.e("TAG", "NewTask:task is ${viewModel.taskUiState} ", )
                    navigateBack()

                }
            },
            modifier = modifier.padding(paddingValues),
            text = R.string.addNewTask
        )

    }



//

}



@Composable
fun InputTask(
    todoModel: TaskUiState,
    modifier: Modifier = Modifier,
    onClick : () -> Unit ,
    onItemValueChange:(TaskDetailse) -> Unit,
    text:Int
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ){
        InputTaskTextFiled(todoModel.taskDetailse, onItemValueChange =  onItemValueChange )
        Spacer(modifier = modifier.padding(16.dp))
        Button(onClick = onClick, modifier = modifier.fillMaxWidth(),enabled =todoModel.isEntryValid ) {
            Text(text = stringResource(id = text))
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
            onItemValueChange(todoModel.copy(taskName = it))
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
            onItemValueChange(todoModel.copy(taskDesc = it))
        },
        label = { Text(text = stringResource(id = R.string.taskDesc))},
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled
    )
    var isChecked by remember { mutableStateOf(todoModel.taskDone) }

    Checkbox(checked = isChecked , onCheckedChange = {
        isChecked = it
        todoModel.taskDone = isChecked})
    Log.e("TAG", "InputTaskTextFiled:todoModel is ${todoModel.taskDone} and isChecked is $isChecked   ", )
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview(){
    TODO_LIST_COMPOSETheme {


    }
}