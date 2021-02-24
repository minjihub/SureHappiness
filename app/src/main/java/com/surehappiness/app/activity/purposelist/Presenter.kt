package com.surehappiness.app.activity.purposelist

import android.content.Context
import com.surehappiness.app.activity.purposelist.adapter.NewPurposeListAdapter

class Presenter constructor(private val view: Contract.View): Contract.Presenter, Contract.RequiredPresenter {

    private val model = Model(this)

    override fun setUserInfo(name: String) {
        view.setUserInfo(name)
    }

    override fun getUserInfoPreference() {
        model.getUserInfoPreference()
    }

    override fun getContext(): Context? {
        return view.getContext()
    }

    override fun makePurposeList() {
        model.makePurposeList()
    }

    override fun setAdapter(adapter: NewPurposeListAdapter) {
        view.setAdapter(adapter)
    }

    override fun showDialog(message: Int) {
        view.showDialog(message)
    }

    override fun makeSearchList(query: String?) {
        model.makeSearchList(query)
    }
}