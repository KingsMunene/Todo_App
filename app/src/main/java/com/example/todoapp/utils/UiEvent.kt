package com.example.todoapp.utils

// This sealed class provides events that happen to the ui not directly to the list of todo or todo items
sealed class UiEvent{
    data class ShowSnackBar(
        val message: String,
        val actionLabel: String
    ): UiEvent()

    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
}
