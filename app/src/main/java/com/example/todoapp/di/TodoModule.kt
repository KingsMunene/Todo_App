package com.example.todoapp.di

import android.app.Application
import androidx.room.Room
import com.example.todoapp.data.TodoDatabase
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.repository.TodoRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    // This creates an instance of TodoDatabase
    @Provides
    @Singleton
    fun providesTodoDatabase(application: Application): TodoDatabase {
        return Room.databaseBuilder(
            application,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    // This consumes the database created and provides a repository through the implementation class
    @Provides
    @Singleton
    fun providesTodoRepository(database: TodoDatabase): TodoRepository{
        return TodoRepositoryImplementation(database.todoDao)
    }
}