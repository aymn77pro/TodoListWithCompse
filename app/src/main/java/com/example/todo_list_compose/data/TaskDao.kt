package com.example.todo_list_compose.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTask():Flow<List<TodoModel>>

    @Query("SELECT * FROM tasks WHERE :id = idn")
      fun getOneTask(id:Int):Flow<TodoModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TodoModel)

    @Update
    suspend fun updateTask(task: TodoModel)

    @Delete
    suspend fun deleteTask(Task: TodoModel)

}