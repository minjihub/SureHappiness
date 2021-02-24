package com.surehappiness.app.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.surehappiness.app.R
import com.surehappiness.app.databinding.NewLayoutCreatePurposeBinding
import java.util.*

class NewCreatePurpose : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: NewLayoutCreatePurposeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.layout_create_purpose)

        binding = DataBindingUtil.setContentView(this, R.layout.layout_create_purpose)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)


        binding.startDate.setOnClickListener(this)
        binding.endDate.setOnClickListener(this)
        binding.stampCount.setOnClickListener(this)
        binding.createBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        when(view!!.id){
            //break 필요한가
            R.id.start_date -> showStartDateDialog()

            R.id.end_date -> showEndDateDialog()

            R.id.stamp_count ->{
                if(TextUtils.isEmpty(binding.purposeName.text))
                    binding.notice.text = "* 목표명을 입력해주세요."
                else if(TextUtils.isEmpty(binding.startDate.text))
                    binding.notice.text = "* 시작일을 입력해주세요."
                else if(TextUtils.isEmpty(binding.endDate.text))
                    binding.notice.text = "* 완료일을 입력해주세요."
                else if(TextUtils.isEmpty(binding.stampCount.text))
                    binding.notice.text = "* 목표 실천수(스탬프 수)를 입력해주세요."
                else{
                    showCountPickerDialog()
                }
            }
        }
    }

    fun showStartDateDialog(){
        val date = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            date.set(Calendar.YEAR, year)
            date.set(Calendar.MONTH, month)
            date.set(Calendar.DAY_OF_MONTH, day)
        }

        val dateDialog = DatePickerDialog(this, dateSetListener, )

    }

    fun showEndDateDialog(){

    }

    fun showCountPickerDialog(){

    }

}