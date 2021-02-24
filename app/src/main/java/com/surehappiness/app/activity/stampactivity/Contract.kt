package com.surehappiness.app.activity.stampactivity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.activity.stampactivity.adapter.NewStampOnViewAdapter
import com.surehappiness.app.dto.Purpose
import com.surehappiness.app.dto.Stamp

interface Contract {

    interface View{
        fun getContext(): Context?
        fun setPurposeInfo(purpose: Purpose)
        fun setAdapter(adapter: NewStampOnViewAdapter)
        fun showCommonDialog(message: Int)
        fun successPurpose()
        fun deleteResult(message: String)
        fun getAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>?
        fun reflashListView()
    }

    interface Presenter{
        fun makeStampList(purposeId: Int)
        fun addStamp()
        fun deletePurpose()
    }

    interface RequiredPresenter{
        fun getContext(): Context?
        fun setPurposeInfo(purpose: Purpose)
        fun setAdapter(adapter: NewStampOnViewAdapter)
        fun showCommonDialog(message: Int)
        fun successPurpose()
        fun deleteResult(message: String)
        fun getAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>?
        fun reflashListView()
    }
}