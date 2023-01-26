package com.example.todo_list_compose.ui.screenes.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.navigation.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.detiles.InputTask
import com.example.todo_list_compose.ui.screenes.viewModel.EditTaskViewModel
import com.example.todo_list_compose.ui.theme.TODO_LIST_COMPOSETheme
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
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar ={
           TodoListTopAppBar(
               title = stringResource(id = EditDestination.titleRes),
               canNavigateBack = true,
               navigateUp = onNavigateUp )
        }) {paddingValues ->
        EditScreen(
            navigateBack = navigateBack, modifier = modifier.padding(paddingValues))
}
}


@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditTaskViewModel = viewModel(factory = AppViewModelProvider.Factory )

){
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        InputTask(
            todoModel = viewModel.uiEditState ,
            onClick = {
                coroutineScope.launch {
                    viewModel.updateTask()
                    navigateBack()
                }
            },
            onItemValueChange = viewModel::updateUiState,
            text = R.string.editTask
        )
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        OutlinedButton(
            onClick = {
                deleteConfirmationRequired = true
            } ,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Red,
                backgroundColor = Color.Transparent
            ),
            border = BorderStroke(1.dp, Color.Red)
        ){
            Box {
                Text(modifier= modifier.align(Alignment.Center),
                    text = stringResource(id = R.string.delete_Task)
                )
            }
        }
        if (deleteConfirmationRequired){
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    coroutineScope.launch { viewModel.deleteTask() }
                    navigateBack()
                },
                onDeleteCancel = { deleteConfirmationRequired = false })
        }


    }



}


@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        }
    )
}


@Preview(showSystemUi = true)
@Composable
fun x (){
    TODO_LIST_COMPOSETheme {
     EditScreen(navigateBack = {  })   
    }
}