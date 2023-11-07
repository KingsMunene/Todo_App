package com.example.todoapp.ui.add_edit_todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.utils.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTodo(
    popBackStack: () -> Unit,
    viewModel: Add_edit_todo_viewModel = hiltViewModel()
) {
    val snackbarHostState = remember{ SnackbarHostState() }

    LaunchedEffect(key1 = true ){
        viewModel.uiEvents.collect{ event ->
            when(event){
                UiEvent.PopBackStack -> popBackStack()
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                else -> Unit
            }

        }
    }

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = {viewModel.onEvent(Add_Edit_todo_events.SaveTodo)}) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Add new todo" )
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {_->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.title ,
                onValueChange = { viewModel.onEvent(Add_Edit_todo_events.EditTitle(it)) },
                placeholder = {Text("Todo Title")},
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = viewModel.description ,
                onValueChange = { viewModel.onEvent(Add_Edit_todo_events.EditDescription(it)) },
                placeholder = {Text("Todo description")},
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )

        }

    }

}