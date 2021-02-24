package com.surehappiness.app.activity.stampactivity.adapter

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.databinding.NewStampItemBinding
import com.surehappiness.app.dto.Stamp
import com.surehappiness.app.retrofit.RetrofitNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NewStampOnViewAdapter(private var items: List<Stamp>): RecyclerView.Adapter<NewStampOnViewAdapter.ViewHolder>() {
    private lateinit var ctx: Context

    fun setItems(items: List<Stamp>){
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        ctx = parent.context
        val binding: NewStampItemBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.new_stamp_item, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(items[0].idx != 0){
            holder.bind(items[position])
        }

        holder.setIsRecyclable(false)
    }

    inner class ViewHolder(val binding: NewStampItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private val stampCall = RetrofitNetwork.getInstance().stampCall

        fun bind(item: Stamp){
            if(item.updateDate != ""){
                binding.stampYear.text = item.updateDate.substring(0,4)
                binding.stampDate.text = item.updateDate.substring(5)

                binding.stampYear.visibility = View.VISIBLE
                binding.stampDate.visibility = View.VISIBLE

                binding.stampView.setBackgroundResource(R.drawable.stamp_on_circle)

                if(item.purpose.purposeState == "P"){
                    binding.root.setOnClickListener {
                        val date = Calendar.getInstance()

                        val datePickerDialog = DatePickerDialog(ctx, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                            // 선택 날짜
                            val newDate = Calendar.getInstance()
                            newDate.set(year, monthOfYear, dayOfMonth)

                            val requestStamp = Stamp()
                            requestStamp.updateDate = SimpleDateFormat("yyyy-MM-dd").format(newDate.time)

                            val call = stampCall.updateDateStamp(item.idx, requestStamp)
                            call.enqueue(object : Callback<Stamp> {
                                override fun onResponse(call: Call<Stamp>, response: Response<Stamp>) {
                                    if (response.body().idx != 0) {
                                        binding.stampYear.text = requestStamp.updateDate.substring(0,4)
                                        binding.stampDate.text = requestStamp.updateDate.substring(5)
                                        Toast.makeText(ctx, "스탬프 날짜 수정 완료", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(ctx, "스탬프 날짜 수정 실패", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<Stamp>, t: Throwable) {
                                    Toast.makeText(ctx, "서버 연결이 원활하지 않습니다.", Toast.LENGTH_SHORT).show()
                                }
                            })

                        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
                        datePickerDialog.datePicker.maxDate = Date().time
                        datePickerDialog.show()
                    }
                }
            }
        }
    }
}