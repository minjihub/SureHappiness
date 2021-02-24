package com.surehappiness.app.activity.findaccount

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.surehappiness.app.activity.NewFindIDFrangment
import com.surehappiness.app.activity.NewFindPWFragment

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return if(position == 0)
            NewFindIDFrangment()
        else
            NewFindPWFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0)
            "아이디 찾기"
        else
            "비밀번호 찾기"
    }
}