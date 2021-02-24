package com.surehappiness.app.activity.successlist

import android.content.Context
import com.surehappiness.app.activity.successlist.adapter.NewSuccessListAdapter

class Presenter  constructor(private val view: Contract.View): Contract.Presenter, Contract.RequiredPresenter {

    private val model = Model(this)

    override fun makeSuccessList() {
        model.makeSuccessList()
    }

    override fun getContext(): Context? {
        return view.getContext()
    }

    override fun setAdapter(adapter: NewSuccessListAdapter) {
        view.setAdapter(adapter)
    }

    override fun showDialog(message: String) {
        view.showDialog(message)
    }

    override fun setTotalStamp(total: Int) {
        view.setTotalStamp(total)
    }
}