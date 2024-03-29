package com.vperelygin.todoapp.viewmodel

import android.app.Application
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.vperelygin.todoapp.model.Todo
import com.vperelygin.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application): AndroidViewModel(application), CoroutineScope {

       val todoLD = MutableLiveData<List<Todo>>()
    private var job = Job()

    fun refresh(){
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
            TodoDatabase::class.java,
                "  tododb"
            ).build()
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    fun clearTask(todo: Todo){
        launch {
            val db = Room.databaseBuilder(
                getApplication(),
                TodoDatabase::class.java,
                " tododb"
            ).build()
            db.todoDao().deleteTodo(todo)
            todoLD.value = db.todoDao().selectAllTodo()
        }
    }

    override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

}