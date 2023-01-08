package com.example.todo_list_compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo_list_compose.ui.screenes.detiles.NewTask
import com.example.todo_list_compose.ui.screenes.detiles.NewTaskDestination
import com.example.todo_list_compose.ui.screenes.screens.*


@Composable
fun TodoNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
){
    NavHost(
        navController = navController ,
        startDestination = HomeTaskDestination.route,
        modifier = modifier){
        composable(route = HomeTaskDestination.route){
        TodoListAppScreen(
            navigateToItemEntry = {
                               navController.navigate(NewTaskDestination.route)
            },
            navigateToItemUpdate = {
                navController.navigate("${DetilesDestination.route}/${it}")
            } )
        }
        composable(route = NewTaskDestination.route ){
            NewTask(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
        composable(
            route = DetilesDestination.routeWithArgs,
            arguments = listOf(navArgument(DetilesDestination.taskIdArg){
                type = NavType.IntType
            })
        ){
            DetilesTask(
                navigateToEditItem ={ navController.navigate("${EditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
                )
        }
        composable(
            route = EditDestination.routeWithArgs,
            arguments = listOf(navArgument(EditDestination.taskIdArg){
                type = NavType.IntType
            })
        ){
            EditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() })
        }
    }
}