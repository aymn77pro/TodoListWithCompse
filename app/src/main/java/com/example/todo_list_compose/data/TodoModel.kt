package com.example.todo_list_compose.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.IDN

@Entity(tableName = "tasks")
data class TodoModel(
    var taskName : String,
    var taskDescription: String,
    var taskDone:Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val idn: Int = 0
)

