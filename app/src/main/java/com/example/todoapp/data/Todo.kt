package com.example.todoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// A table for the todo app
@Entity
data class Todo(
    val title: String,
    val description: String,
    val isDone: Boolean,
    @PrimaryKey val todoId: Int?
)
