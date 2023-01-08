package com.example.todo_list_compose.ui.screenes.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo_list_compose.R
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.navigation.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.detiles.InputTaskTextFiled
import com.example.todo_list_compose.ui.screenes.viewModel.DeleteTaskViewModel
import kotlinx.coroutines.launch

object DetilesDestination : NavigationDestination {
    override val route: String = "details"
    override val titleRes: Int = R.string.task_details
    const val taskIdArg = "taskId"
    val routeWithArgs = "$route/{$taskIdArg}"
}


@Composable
fun DetilesTask(
    modifier: Modifier = Modifier,
    viewModel: DeleteTaskViewModel = viewModel( factory = AppViewModelProvider.Factory ),
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    ){
    val uiState = viewModel.uiStat.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TodoListTopAppBar(
                title = stringResource(id = DetilesDestination.titleRes) ,
                canNavigateBack =true,
                navigateUp = navigateBack)
        },
        floatingActionButton = {
            Button(onClick = { navigateToEditItem(uiState.value.id) },
                modifier.navigationBarsPadding()) {
                Text(text = stringResource(id = R.string.editTask))
            }
        }
    ) { innerPadding ->
        DetilesScreen(
            uiState = uiState.value,
            onDelete =  {
            coroutineScope.launch {
                viewModel.deleteTask()
                navigateBack()
            }

        },
            modifier = modifier.padding(innerPadding)
        )
    }

        
    }



@Composable
fun DetilesScreen(
    modifier: Modifier = Modifier,
    uiState: TaskDetailse,
    onDelete : ()->Unit
    ){
    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        InputTaskTextFiled(todoModel = uiState, onItemValueChange = {}, enabled = false )
        Divider()
        OutlinedButton(
            onClick = {deleteConfirmationRequired = true} ,
            modifier = modifier.fillMaxWidth()
        ) {
    Text(text = stringResource(id = R.string.delete_Task))
        }
        
        if (deleteConfirmationRequired){
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
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