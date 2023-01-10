package com.example.todo_list_compose.ui.screenes.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.room.util.TableInfo.Column
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todo_list_compose.R
import com.example.todo_list_compose.data.TodoModel
import com.example.todo_list_compose.ui.AppViewModelProvider
import com.example.todo_list_compose.ui.navigation.NavigationDestination
import com.example.todo_list_compose.ui.screenes.TaskDetailse
import com.example.todo_list_compose.ui.screenes.TodoListTopAppBar
import com.example.todo_list_compose.ui.screenes.TodoListViewModel


object HomeTaskDestination:NavigationDestination {
    override val route: String = "Home"
    override val titleRes: Int = R.string.app_name
}


@Composable
fun TodoListAppScreen(
    modifier: Modifier = Modifier,
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    viewModel:TodoListViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Scaffold(
        topBar = {
            TodoListTopAppBar(
                title = stringResource(id = HomeTaskDestination.titleRes) ,
                canNavigateBack = false,
            ) },
        floatingActionButton =
        {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                modifier = Modifier.navigationBarsPadding()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.task_entry_title),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) {paddingValues ->
        Divider()
        HomeScreen(tasks = homeUiState.task ,
            onClick = navigateToItemUpdate,
            modifier = modifier.padding(paddingValues))
    }

        }


@Composable
fun HomeScreen(
    tasks:List<TodoModel>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
){
    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        ){
    if (tasks.isEmpty()){
        Lottie()
    } else {
        TaskList(tasks,
            onClick = {
                onClick(it.idn)
                })
    }
    }
}


@Composable
fun TaskList(todoModel: List<TodoModel>,modifier: Modifier = Modifier,onClick:(TodoModel)->Unit) {
    val list = todoModel

    LazyColumn{
        items(
            items =  list,
            key = {task -> task.idn}){ it ->
           CardTask(
               todoModel = it,
               modifier = modifier,
               onClick = onClick

           )
        }
    }
}


@Composable
fun Lottie(){

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.data))
    val progress by animateLottieCompositionAsState(composition)

    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}