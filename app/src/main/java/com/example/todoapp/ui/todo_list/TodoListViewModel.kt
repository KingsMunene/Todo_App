package com.example.todoapp.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.utils.Routes
import com.example.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
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
    private val _uiEvents = Channel<UiEvent>()

    // This makes sure that the events are received as hot when needed only
    val uiEvents = _uiEvents.receiveAsFlow()

    // A variable to hold the deleted todo incase the user needs to undo the action
    private var deletedTodo: Todo? = null
    // onEvent function that implements the todoList events when they happen

    fun onEvent(event: TodoListEvents){
        when (event){
            TodoListEvents.ClickAddNewTodo -> {
                sendEvent(UiEvent.Navigate(Routes.AddTodo.name))
            }
            is TodoListEvents.ClickTodo -> {
                sendEvent(UiEvent.Navigate(Routes.AddTodo.name + "?todoId=${event.todo.todoId}"))
            }
            TodoListEvents.ClickUndo -> {
                deletedTodo?.let {
                   viewModelScope.launch{
                        repository.insertTodo(deletedTodo!!)
                   }
                }

            }
            is TodoListEvents.DeleteTodo -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendEvent(UiEvent.ShowSnackBar(
                        "Todo Item deleted successfully",
                        "Undo"
                    ))
                }
            }
            is TodoListEvents.TodoDone -> {
                viewModelScope.launch {
                    repository.insertTodo(event.todo.copy(isDone = event.isDone))
                }
            }
        }
    }

    private fun sendEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }




}