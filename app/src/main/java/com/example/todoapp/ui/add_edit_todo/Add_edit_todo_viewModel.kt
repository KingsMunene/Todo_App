package com.example.todoapp.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.Todo
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Add_edit_todo_viewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    // A todovariable to hold a todoitem if it has been clicked from the list
    var todoItem by mutableStateOf<Todo?>(null)
        private set
    var title: String by mutableStateOf("")
        private set
    var description: String by mutableStateOf("")
        private set

    // Ui events that happen when needed in our application
    private val _uiEvents = Channel<UiEvent>()

    // This makes sure that the events are received as hot when needed only
    val uiEvents = _uiEvents.receiveAsFlow()

    init{
        val todoId = savedStateHandle.get<Int>("todoId")

        if (todoId != -1){
            viewModelScope.launch {
                repository.getTodoById(todoId!!)?.let {
                    title = it.title
                    description = it.description
                    this@Add_edit_todo_viewModel.todoItem = it
                }
            }
        }
    }

    fun onEvent(event: Add_Edit_todo_events){
        when(event){
            is Add_Edit_todo_events.EditDescription -> {
                description = event.description
            }
            is Add_Edit_todo_events.EditTitle -> {
                title = event.title
            }
            is Add_Edit_todo_events.SaveTodo -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendEvent(UiEvent.ShowSnackBar(
                            message = "Title Cannot Be blank",
                            actionLabel = null
                        ))
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todoItem?.isDone ?: false,
                            todoId = todoItem?.todoId
                        )
                    )

                    sendEvent(UiEvent.PopBackStack)

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