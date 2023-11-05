package com.example.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Hilt application class that is required for every hilt android application
@HiltAndroidApp
class TodoApp: Application()