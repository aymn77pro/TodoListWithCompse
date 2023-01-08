package com.example.todo_list_compose

import android.app.Application
import com.example.todo_list_compose.data.AppDataBase

class TodoListApp: Application() {

    val database: AppDataBase by lazy {
            AppDataBase.getDatabase(this)

    }


}