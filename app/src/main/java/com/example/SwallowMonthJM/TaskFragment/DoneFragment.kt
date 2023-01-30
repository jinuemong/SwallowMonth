package com.example.SwallowMonthJM.TaskFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.SwallowMonthJM.Adapter.TaskListAdapter
import com.example.SwallowMonthJM.Adapter.TodayRoutineAdapter
import com.example.SwallowMonthJM.MainActivity
import com.example.SwallowMonthJM.Model.DayData
import com.example.SwallowMonthJM.Model.Routine
import com.example.SwallowMonthJM.R
import com.example.SwallowMonthJM.Unit.RoutineSlider
import com.example.SwallowMonthJM.Unit.TaskSlider
import com.example.SwallowMonthJM.databinding.FragmentDoneBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class DoneFragment(
    private val slideFrame : SlidingUpPanelLayout,
    private val slideLayout: View
) : Fragment() {
    private var _binding: FragmentDoneBinding?=null
    private val binding get() = _binding!!
    lateinit var mainActivity: MainActivity
    private lateinit var taskListAdapter:TaskListAdapter
    private lateinit var dayData : DayData //task 리스트 불러오기
    private lateinit var routineListAdapter:TodayRoutineAdapter
    private var taskSlider :View  = slideLayout.findViewById(R.id.slide_task)
    private var routineSlider : View = slideLayout.findViewById(R.id.slide_routine)

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
        _binding = FragmentDoneBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun initView(){

        //루틴 어댑터 초기화
        initRoutineAdapter()
        //task 어댑터 초기화
        dayData = mainActivity.viewModel.currentMonthArr[mainActivity.viewModel.currentDayPosition.value!!]
        initTaskAdapter(dayData)

        //루틴 데이터 변화 관찰
        mainActivity.routineViewModel.routineLivData.observe(mainActivity, Observer {
            routineListAdapter.setData(it)
        })

        //task 데이터 변화 관찰
        mainActivity.viewModel.dayLiveData.observe(mainActivity, Observer {
            dayData.taskList?.let { taskList->
                taskListAdapter.setData(taskList)
            }
        })

        // 날짜 데이터 변경 시 적용
        mainActivity.viewModel.currentDayPosition.observe(mainActivity, Observer { dayIndex->
            //현재 날짜 인덱스의 task 갱신
            dayData = mainActivity.viewModel.currentMonthArr[dayIndex]
            dayData.taskList?.let { taskList->
                taskListAdapter.setData(taskList)
            }

            //루틴 리싸이클러의 현재 날짜 인덱스 변경
            routineListAdapter.setDayDate(dayIndex)
        })


    }

    private fun initTaskAdapter(day : DayData){
        // task 뷰 init
        // 어댑터 생성
        taskListAdapter = TaskListAdapter(mainActivity,day.taskList,true)
        // 어댑터 내부 클릭 이벤트 적용
        taskListAdapter.apply {
            setOnItemClickListener(object :TaskListAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    val task = day.taskList!![position]

                    routineSlider.visibility = View.GONE
                    taskSlider.apply {
                        visibility = View.VISIBLE
                        TaskSlider(this,slideFrame,mainActivity,task)
                            .apply { initSlide() }
                        val state = slideFrame.panelState
                        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                            slideFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                        }
                        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            slideFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }
                    }
                }
            })
        }
        //어댑터 부착
        binding.taskDoneView.adapter  =taskListAdapter
    }

    private fun initRoutineAdapter(){
        //루틴 뷰 init
        // 어댑터 생성
        routineListAdapter = TodayRoutineAdapter(mainActivity,mainActivity.routineViewModel.routineLivData.value!!,
            mainActivity.viewModel.currentDayPosition.value!!,true)
        // 어댑터 내부 클릭 이벤트 적용
        routineListAdapter.apply {
            setOnItemClickListener(object :TodayRoutineAdapter.OnItemClickListener{
                override fun onItemClick(dayPosition: Int, routine: Routine) {
                    taskSlider.visibility = View.GONE
                    routineSlider.apply {
                        visibility = View.VISIBLE
                        RoutineSlider(this,slideFrame,mainActivity,dayPosition,routine)
                            .apply { initSlide() }

                        val state = slideFrame.panelState
                        if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                            slideFrame.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                        }
                        else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                            slideFrame.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                        }

                    }
                }
            })
        }
        // 어댑터 부착
        binding.routineDoneView.adapter = routineListAdapter
    }

}