package com.example.SwallowMonthJM.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.SwallowMonthJM.MainActivity
import com.example.SwallowMonthJM.Model.DayRoutine
import com.example.SwallowMonthJM.Model.Routine
import com.example.SwallowMonthJM.Unit.levelPoint

class RoutineViewModel(
    mainActivity: MainActivity
): ViewModel(){
    class Factory(val mainActivity: MainActivity) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RoutineViewModel(mainActivity) as T
        }
    }
    private val mainView = mainActivity.viewModel

    var routineLivData = MutableLiveData<ArrayList<Routine>>()

    private var currentRoutineArr = ArrayList<Routine>()
    init {
        routineLivData.value = currentRoutineArr
    }

    fun addRoutineData(routine:Routine){
        //서버에 생성하고 반환값으로 DayRoutine 생성해야함 !
        for (i in 0 until routine.totalRoutine){
            val dayIndex = routine.startNum+i*routine.cycle
            routine.dayRoutinePost.add(DayRoutine(null,routine.routineId!!,mainView.monthData.monthId!!,dayIndex,false))
        }
        currentRoutineArr.add(routine)
        routineLivData.value = currentRoutineArr
    }

    fun delRoutineData(routine: Routine){
        currentRoutineArr.remove(routine)
        routineLivData.value = currentRoutineArr
    }

    fun doneRoutineData(routine: Routine,dayRoutine: DayRoutine){
        if(!dayRoutine.clear) {
            //one run
            routine.clearRoutine += 1
            mainView.monthData.totalPoint+= levelPoint[0]
        }
        dayRoutine.clear = true
        routineLivData.value = currentRoutineArr
    }

}