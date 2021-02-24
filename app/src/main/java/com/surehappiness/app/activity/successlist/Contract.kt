package com.surehappiness.app.activity.successlist

import android.content.Context
import com.surehappiness.app.activity.successlist.adapter.NewSuccessListAdapter

interface Contract {

    interface View{
        fun getContext(): Context?
        fun setTotalStamp(total: Int)
        fun setAdapter(adapter: NewSuccessListAdapter)
        fun showDialog(message: String)
    }

    interface Presenter{
        fun makeSuccessList()
    }

    interface RequiredPresenter{
        fun getContext(): Context?
        fun setTotalStamp(total: Int)
        fun setAdapter(adapter: NewSuccessListAdapter)
        fun showDialog(message: String)
    }
}