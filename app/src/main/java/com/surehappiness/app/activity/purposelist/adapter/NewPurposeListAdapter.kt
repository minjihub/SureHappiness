package com.surehappiness.app.activity.purposelist.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.activity.NewStampActivity
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.databinding.NewPurposeItemBinding
import com.surehappiness.app.dto.Purpose

class NewPurposeListAdapter(private val items: List<NewPurposeInfo>): RecyclerView.Adapter<NewPurposeListAdapter.ViewHolder>() {
    private lateinit var ctx: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctx = parent.context
        val binding: NewPurposeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.new_purpose_item, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: NewPurposeItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewPurposeInfo){
            when(item.status){
                 "P" -> binding.state.setColorFilter(ContextCompat.getColor(ctx, R.color.colorOnGoing))
                 "S" -> binding.state.setColorFilter(ContextCompat.getColor(ctx, R.color.colorSuccess))
                 "F" -> binding.state.setColorFilter(ContextCompat.getColor(ctx, R.color.colorFail))
            }

            if(item.purposeMemo == "")
                binding.purposeScript.text = "목표 설명이 없습니다."
            else
                binding.purposeScript.text = item.purposeMemo

            binding.purposeName.text = item.purpose
            binding.totalStamp.text = "${item.userStampCount}/${item.stampNum}"

            binding.purposeItem.setOnClickListener {
                val intent = Intent(ctx, NewStampActivity::class.java)

//                intent.putExtra("purposeId", item.id)
//                intent.putExtra("purposeState", item.status)

                intent.putExtra("purpose", item)

                ctx.startActivity(intent)
            }
        }

    }
}