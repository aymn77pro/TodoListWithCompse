package com.example.todo_list_compose.ui.screenes.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_list_compose.R
import com.example.todo_list_compose.data.TodoModel
import com.example.todo_list_compose.ui.theme.TODO_LIST_COMPOSETheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardTask(todoModel: TodoModel,modifier: Modifier = Modifier,onClick: (TodoModel) -> Unit ) {
    val expanded by remember { mutableStateOf(false) }
    //val checkedState = remember { mutableStateOf(true) }


    Card(
        elevation = 4.dp,
        modifier = modifier.padding(8.dp),

    ) {
        Column(
            modifier = modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                TaskInformtion(todoModel)
                Spacer(Modifier.weight(1f))
                TaskButton(expend = expanded, onClick = { expanded != expanded})
            }
            if (expanded) TaskDescription(todoModel = todoModel, onClick = onClick)
        }
    }
}


@Composable
fun TaskInformtion(todoModel: TodoModel, modifier:Modifier = Modifier) {
    Checkbox(
        checked = todoModel.taskDone,
        onCheckedChange = { todoModel.taskDone = it },
        modifier = modifier.padding(16.dp, vertical = 8.dp)
    )
    Text(
        text = todoModel.taskName,
        modifier = modifier.padding(16.dp),
        style = MaterialTheme.typography.h6
    )
}

@Composable
private fun TaskButton(
    expend : Boolean,
    onClick : () -> Unit ,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick) {
        Icon(imageVector = Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description)
        )    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCard(){
    TODO_LIST_COMPOSETheme {

    }}

@Composable
private fun TaskDescription(todoModel: TodoModel, modifier: Modifier = Modifier,onClick: (TodoModel) -> Unit){
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.h3,
        )
        Text(
            text = todoModel.taskDescription,
            style = MaterialTheme.typography.body1,
        )
        Button(onClick = { onClick }) {
            Text(text = stringResource(id = R.string.editTask))

        }
    }
}