package com.example.todoapp.repository

import com.example.todoapp.data.Todo
import com.example.todoapp.data.TodoDao
import kotlinx.coroutines.flow.Flow

// This class implements the todo repository that calls the functions in the dao
// The class is then implemented by dependency injection hilt
// We inject a dao in this class that will be provided by the database
class TodoRepositoryImplementation(
    private val todoDao: TodoDao
): TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id: Int): Todo {
        return todoDao.getTodoById(id)
    }

    override fun getTodoList(): Flow<List<Todo>> {
        return todoDao.getTodoList()
    }

}