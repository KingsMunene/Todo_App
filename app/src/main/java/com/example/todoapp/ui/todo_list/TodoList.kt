package com.example.todoapp.ui.todo_list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.utils.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoList(
    navigate:(UiEvent.Navigate) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    // Snack bar host state for showing a snack bar when needed
    val snackbarHostState = remember{ SnackbarHostState() }

    // Collect the list of todoItems in the database. The list is in flow state thus we need to
    //  collect as State
    val todoList = viewModel.todoList.collectAsState(initial = emptyList())
    LaunchedEffect(key1 = 1){
        viewModel.uiEvents.collect{ event ->
            when(event){
                is UiEvent.Navigate -> navigate(event)
                is UiEvent.ShowSnackBar -> {
                    val result = snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actionLabel
                    )

                    // The result variable checks if the snackbar button has been clicked
                    // If it has been clicked then we undo the delete action
                    if (result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(TodoListEvents.ClickUndo)
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onEvent(TodoListEvents.ClickAddNewTodo)}) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add a new todo")
            }
        }
    ) {_->
        LazyColumn{
            items(todoList.value){ todo ->
                TodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    todoItemClicked = {viewModel.onEvent(TodoListEvents.ClickTodo(todo))}
                )

            }
        }

    }

}