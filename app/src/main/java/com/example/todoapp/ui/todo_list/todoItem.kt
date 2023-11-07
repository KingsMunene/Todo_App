package com.example.todoapp.ui.todo_list

import android.widget.CheckBox
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.data.Todo

// TodoItem composable
@Composable
fun TodoItem(
    todo: Todo,
    onEvent:(TodoListEvents) -> Unit,
    todoItemClicked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = todoItemClicked)
    ){
      Column(
          modifier = Modifier
              .weight(1f),
          verticalArrangement = Arrangement.Center
      ) {
          Row(
              verticalAlignment = Alignment.CenterVertically
          ) {
              Text(
                  text = todo.title,
                  style = MaterialTheme.typography.titleMedium
              )

              Spacer(modifier = Modifier.width(16.dp))

              IconButton(
                  onClick = {onEvent(TodoListEvents.DeleteTodo(todo))}
              ) {
                  Icon(
                      imageVector = Icons.Default.Delete,
                      contentDescription = "Delete Todo Item"
                  )

              }

          }

          Spacer(modifier = Modifier.height(16.dp))

          Text(
              text = todo.description,
              style = MaterialTheme.typography.bodyMedium
          )
      }
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = {onEvent(TodoListEvents.TodoDone(todo, isDone = it))}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun todoItemPrev() {
//    TodoItem()
}