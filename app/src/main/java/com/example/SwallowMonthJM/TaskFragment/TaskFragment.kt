package com.example.SwallowMonthJM.TaskFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.SwallowMonthJM.Adapter.TaskListAdapter
import com.example.SwallowMonthJM.MainActivity
import com.example.SwallowMonthJM.databinding.FragmentTaskBinding


class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding?=null
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
        _binding = FragmentTaskBinding.inflate(inflater,container,false)
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
        val day = mainActivity.viewModel.currentMonthArr[mainActivity.viewModel.currentDayPosition]
        binding.taskView.apply {
            adapter = TaskListAdapter(mainActivity,day.taskList,"task")
        }
        mainActivity.viewModel.taskLiveData.observe(mainActivity, Observer {
            day.taskList?.let {
                (binding.taskView.adapter as TaskListAdapter).setData(it)
            }
        })
    }
}