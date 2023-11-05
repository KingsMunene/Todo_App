package com.example.todoapp.ui.todo_list

import com.example.todoapp.data.Todo


// TodoList events.
// This are the events that are sent when we interact with todo List
sealed class TodoListEvents{
    data class DeleteTodo(val todo: Todo): TodoListEvents()

    data class TodoDone(val todo: Todo, val isDone: Boolean): TodoListEvents()

    data class ClickTodo(val todo:Todo): TodoListEvents()

    object ClickAddNewTodo : TodoListEvents()
    object ClickUndo: TodoListEvents()


}
