package com.example.todoapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

// Todo app database
@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoDao: TodoDao
}