package com.example.todoapp.ui.todo_list

import androidx.lifecycle.ViewModel
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

// TodoList ViewModel.
// We inject the todoRepository to access the dao functions
@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {
    // list of todos
    val todoList = repository.getTodoList()

    // Ui events that happen when needed in our application
    private val _UiEvents = Channel<UiEvent>()

    // This makes sure that the events are received as hot when needed only
    val uiEvents = _UiEvents.receiveAsFlow()




}