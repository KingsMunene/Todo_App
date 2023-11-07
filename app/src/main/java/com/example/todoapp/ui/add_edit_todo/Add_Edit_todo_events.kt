package com.example.todoapp.ui.add_edit_todo

import com.example.todoapp.data.Todo

sealed class Add_Edit_todo_events{
    data class EditTitle(val title: String): Add_Edit_todo_events()

    data class EditDescription(val description: String): Add_Edit_todo_events()

    object SaveTodo: Add_Edit_todo_events()
}
