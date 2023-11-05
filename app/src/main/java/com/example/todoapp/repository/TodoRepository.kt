package com.example.todoapp.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.data.Todo
import kotlinx.coroutines.flow.Flow

// This repository is used to collect all the data that is needed in the viewModel
interface TodoRepository {
    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(id: Int): Todo


    fun getTodoList(): Flow<List<Todo>>
}