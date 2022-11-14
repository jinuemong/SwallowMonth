package com.example.SwallowMonthJM.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.SwallowMonthJM.Unit.Profile
import com.example.SwallowMonthJM.Unit.Todo

//유저 프로필, 리스트 관리
class MainViewModel : ViewModel(){
    private val userProfile: Profile? = null

    var recentlyAddData = MutableLiveData<HashMap<String,ArrayList<Todo>>>()
    var todoData = HashMap<String, ArrayList<Todo>>()
    var currentMonthArr = ArrayList<Todo>()

    fun addTodoData(key:String,text:String){
        if (todoData[key] !=null){
            currentMonthArr = todoData[key]!!
        }else{
            currentMonthArr = ArrayList()
        }
        currentMonthArr.add(Todo(null,key,text,false))
        todoData[key] = currentMonthArr

        recentlyAddData.value = todoData

    }

    fun delTodoData(key:String,todo:Todo){
        todoData[key]?.remove(todo)
        recentlyAddData.value = todoData
    }

    fun doneData(todo:Todo){
        todo.isDone = !todo.isDone
        recentlyAddData.value = todoData
    }
}