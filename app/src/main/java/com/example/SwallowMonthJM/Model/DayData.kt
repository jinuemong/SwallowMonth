package com.example.SwallowMonthJM.Model

import java.io.Serializable

class DayData(
    var dayDateId :Int?,
    var keyDate : String,
    var day :Int,
    var isSelected : Boolean,
    var monthIndex : Int,
    var taskList : ArrayList<Task>?,
):Serializable