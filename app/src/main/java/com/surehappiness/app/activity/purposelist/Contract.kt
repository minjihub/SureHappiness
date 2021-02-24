package com.surehappiness.app.activity.purposelist

import android.content.Context
import com.surehappiness.app.activity.purposelist.adapter.NewPurposeListAdapter

interface Contract {

    interface View{
        fun getContext(): Context?
        fun setUserInfo(name: String)
        fun setAdapter(adapter: NewPurposeListAdapter)
        fun showDialog(message: Int)
    }

    interface Presenter{
        fun getUserInfoPreference()
        fun makePurposeList()
        fun makeSearchList(query: String?)
    }

    interface RequiredPresenter{
        fun getContext(): Context?
        fun setUserInfo(name: String)
        fun setAdapter(adapter: NewPurposeListAdapter)
        fun showDialog(message: Int)
    }

}