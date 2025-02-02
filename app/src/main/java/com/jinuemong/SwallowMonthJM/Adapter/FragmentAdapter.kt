package com.jinuemong.SwallowMonthJM.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
//MainActivity 의 fragment adapter
open class FragmentAdapter(fa:FragmentActivity):FragmentStateAdapter(fa) {
    var fragments : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int =fragments.size

    override fun createFragment(position: Int):Fragment = fragments[position]

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }


}