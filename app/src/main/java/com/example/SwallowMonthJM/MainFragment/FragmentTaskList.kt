package com.example.SwallowMonthJM.MainFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.SwallowMonthJM.MainActivity
import com.example.SwallowMonthJM.R
import com.example.SwallowMonthJM.Unit.DayData
import com.example.SwallowMonthJM.Unit.dayOfWeek
import com.example.SwallowMonthJM.databinding.FragmentTaskListBinding
import com.example.SwallowMonthJM.databinding.ItemCalendarHorizontalBinding

// home

class FragmentTaskList : Fragment() {
    private var _binding : FragmentTaskListBinding?=null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpListener()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        binding.taskListCalendar.text = mainActivity.viewModel.dateTime
        binding.taskListPer.progress = mainActivity.viewModel.totalPer
        binding.taskListPerText.text = mainActivity.viewModel.totalPer.toString()+"%"
        initRecyclerView()
    }

    private fun setUpListener(){

    }

    private fun initRecyclerView(){
        binding.taskListHoCalendar.apply {
            adapter = CalendarListAdapter(mainActivity,mainActivity.viewModel.currentDate.dateList)
            smoothScrollToPosition(mainActivity.viewModel.currentDate.todayIndex)
        }
    }
}

class CalendarListAdapter(
    private val mainActivity: MainActivity,
    private val dataSet : ArrayList<DayData>
):RecyclerView.Adapter<CalendarListAdapter.CalendarListItemHolder>(){

    class CalendarListItemHolder(val binding : ItemCalendarHorizontalBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarListItemHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_horizontal,parent,false)
        return CalendarListItemHolder(ItemCalendarHorizontalBinding.bind(view))
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: CalendarListItemHolder, position: Int) {
        holder.binding.itemHoDayNum.text = dataSet[position].day.toString()
        holder.binding.itemHoDayText.setText(dayOfWeek[(position%7)])

        //오늘
        if (dataSet[holder.absoluteAdapterPosition].isToday){
            Log.d("dd",holder.absoluteAdapterPosition.toString())
            holder.binding.root.setBackgroundColor(R.color.color_type3)
        }

        holder.binding.root.setOnClickListener {
            val k = mainActivity.viewModel
                .getKeyData(dataSet[holder.absoluteAdapterPosition].monthIndex,
                    dataSet[holder.absoluteAdapterPosition].day.toString())
            Log.d("k",k)
        }
    }

    override fun getItemCount(): Int =dataSet.size
}