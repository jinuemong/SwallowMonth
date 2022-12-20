package com.example.SwallowMonthJM

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.SwallowMonthJM.Adapter.FragmentAdapter
import com.example.SwallowMonthJM.AddTaskRoutineFragment.AddTaskFragment
import com.example.SwallowMonthJM.MainFragment.FragmentCalendar
import com.example.SwallowMonthJM.MainFragment.FragmentRepeatTaskList
import com.example.SwallowMonthJM.MainFragment.FragmentTaskList
import com.example.SwallowMonthJM.MainFragment.FragmentUserUI
import com.example.SwallowMonthJM.ViewModel.AddTaskViewModel
import com.example.SwallowMonthJM.ViewModel.MainViewModel
import com.example.SwallowMonthJM.ViewModel.RoutineViewModel
import com.example.SwallowMonthJM.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()
    val addViewModel: AddTaskViewModel by viewModels()
    val routineViewModel : RoutineViewModel by viewModels()
    lateinit var frManger: FragmentManager
    private lateinit var fragmentPageAdapter: FragmentAdapter
    lateinit var viewPager: ViewPager2

    //click tab
    val tintColor = ColorStateList(
        arrayOf(
            intArrayOf(android.R.attr.state_selected),
            intArrayOf(-android.R.attr.state_selected)
        ),
        intArrayOf(Color.parseColor("#629CD1"), Color.parseColor("#2C608F"))
    )

    //tab Icon
    private val iconView = arrayOf(
        R.drawable.ic_baseline_home_24,
        R.drawable.ic_iconmonstr_bar_chart_thin,
        R.drawable.ic_iconmonstr_refresh_7,
        R.drawable.ic_iconmonstr_user_male_thin
    )
    lateinit var aniList: Array<Animation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //애니메이션 설정
        aniList = arrayOf(
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.enter_left),
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.enter_up),
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.enter_down),
            AnimationUtils.loadAnimation(this@MainActivity, R.anim.wave),
            AnimationUtils.loadAnimation(this@MainActivity,R.anim.enter_right)
        )
        initView()
        setUpListener()

    }

    private fun initView() {
        frManger = this@MainActivity.supportFragmentManager
        binding.mainTopLayout.apply {
            binding.mainTopLayout
        }
        viewPager = binding.mainMidViewpager
        initCurrentDate()
        initFragmentAdapter()
        initViewPager()
        initTabLayout()
    }

    private fun setUpListener(){
        binding.addTaskButton.setOnClickListener {
            onFragmentChange(AddTaskFragment())
        }
    }

    private fun initCurrentDate() {
        val date = Calendar.getInstance().time
        val dateMonth: Int = SimpleDateFormat("MM", Locale.KOREA).format(date).toInt()
        viewModel.apply {
            todayMonth = dateMonth
            initCurrentData(date)
        }
    }

    private fun initFragmentAdapter() {
        fragmentPageAdapter = FragmentAdapter(this@MainActivity)
        fragmentPageAdapter.apply {
            addFragment(FragmentTaskList())
            addFragment(FragmentCalendar())
            addFragment(FragmentRepeatTaskList())
            addFragment(FragmentUserUI())
        }
    }

    private fun initViewPager() {
        viewPager.adapter = fragmentPageAdapter
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        //스크롤 막기
        viewPager.isUserInputEnabled = false
    }

    private fun initTabLayout() {
        binding.mainBottomTabLayout.tabIconTint = tintColor
        TabLayoutMediator(binding.mainBottomTabLayout, viewPager)
        { tab, position ->
            tab.setIcon(iconView[position])
        }.attach()

    }

    fun onFragmentChange(goFragment: Fragment) {
        frManger.beginTransaction().replace(R.id.view_container_in_main, goFragment)
            .addToBackStack(null)
            .commit()
    }

    fun onFragmentGoBack(fragment: Fragment) {
        frManger.beginTransaction().remove(fragment).commit()
        frManger.popBackStack()
    }

}

