package com.surehappiness.app.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.activity.successlist.Contract
import com.surehappiness.app.activity.successlist.Presenter
import com.surehappiness.app.activity.successlist.adapter.NewSuccessListAdapter
import com.surehappiness.app.databinding.NewLayoutSuccessListBinding

class SuccessList: Fragment(), Contract.View {


    private lateinit var binding: NewLayoutSuccessListBinding
    private lateinit var presenter: Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_layout_success_list, container, false)

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, true)
        binding.recyclerVeiw.layoutManager = layoutManager

        presenter = Presenter(this)

        return binding.root
    }

    override fun onStart(){
        super.onStart()

        presenter.makeSuccessList()
    }

    override fun setTotalStamp(total: Int) {
        binding.totalStamp.text = total.toString()
    }

    override fun setAdapter(adapter: NewSuccessListAdapter) {
        binding.recyclerVeiw.adapter = adapter
        binding.recyclerVeiw.scrollToPosition(adapter.itemCount - 1)
    }

    override fun showDialog(message: String) {
        val dialog = AlertDialog.Builder(activity!!)
                .setMessage(message)

        dialog.create().show()
    }
}