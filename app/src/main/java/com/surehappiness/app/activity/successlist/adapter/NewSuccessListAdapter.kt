package com.surehappiness.app.activity.successlist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.activity.NewStampActivity
import com.surehappiness.app.activity.StampActivity
import com.surehappiness.app.databinding.NewSuccessItemBinding
import com.surehappiness.app.dto.Purpose

class NewSuccessListAdapter(private val items: List<Purpose>): RecyclerView.Adapter<NewSuccessListAdapter.ViewHolder>() {

    private lateinit var ctx: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctx = parent.context
        val binding: NewSuccessItemBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.new_success_item, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(items[0].idx != 0){
            holder.bind(items[position])
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder (private val binding: NewSuccessItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Purpose){
            item.purposeName?.let {
                val successName = if(it.length >= 8){
                    it.substring(0,8) + "···"
                }else{
                    it
                }

                val successDate = item.successDate.substring(0,10)
                binding.successPurpose.text = "$successName  $successDate"
            }

            binding.root.setOnClickListener {
                var intent = Intent(it.context, NewStampActivity::class.java)
                intent.putExtra("purposeIdx", item.idx)
                intent.putExtra("purposeState", item.purposeState)
                it.context.startActivity(intent)
            }

        }
    }
}