package com.surehappiness.app.activity.stampactivity

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.activity.stampactivity.adapter.NewStampOnViewAdapter
import com.surehappiness.app.dto.Purpose

class Presenter constructor(private val view: Contract.View): Contract.Presenter, Contract.RequiredPresenter {
    private val model = Model(this)

    override fun makeStampList(purpose: Int) {
        model.makeStampList(purpose)
    }

    override fun getContext(): Context? {
        return view.getContext()
    }

    override fun setAdapter(adapter: NewStampOnViewAdapter) {
        view.setAdapter(adapter)
    }

    override fun showCommonDialog(message: Int) {
        view.showCommonDialog(message)
    }

    override fun setPurposeInfo(purpose: Purpose) {
        view.setPurposeInfo(purpose)
    }

    override fun addStamp() {
        model.addStamp()
    }

    override fun successPurpose() {
        view.successPurpose()
    }

    override fun deletePurpose() {
        model.deletePurpose()
    }

    override fun deleteResult(message: String) {
        view.deleteResult(message)
    }

    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return view.getAdapter()
    }

    override fun reflashListView() {
        view.reflashListView()
    }
}