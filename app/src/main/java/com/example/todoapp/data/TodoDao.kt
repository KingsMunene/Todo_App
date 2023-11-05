package com.example.todoapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.DeleteColumn
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// TodoDao implementation for interacting with the data in the database

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE todoId = :id")
    suspend fun getTodoById(id: Int): Todo

    @Query("SELECT * FROM todo")
    fun getTodoList(): Flow<List<Todo>>

}