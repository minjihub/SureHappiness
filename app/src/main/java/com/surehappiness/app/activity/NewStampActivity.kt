package com.surehappiness.app.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.activity.model.PurposeInfo
import com.surehappiness.app.activity.stampactivity.Contract
import com.surehappiness.app.activity.stampactivity.Presenter
import com.surehappiness.app.activity.stampactivity.adapter.NewStampOnViewAdapter
import com.surehappiness.app.databinding.NewLayoutStampBoardBinding
import com.surehappiness.app.dto.Purpose
import com.surehappiness.app.utils.CommonFunction
import kotlinx.android.synthetic.main.toolbar.view.*

class NewStampActivity: AppCompatActivity(), Contract.View {

    private lateinit var binding: NewLayoutStampBoardBinding
    private lateinit var presenter: Presenter
    private var purposeId: Int? = null
    private var purposeState: String? = null
    private var purposeInfo: Purpose? = null
    private var purpose: NewPurposeInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.new_layout_stamp_board)

        presenter = Presenter(this)

        val toolbar = binding.root.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.recyclerVeiw.layoutManager = GridLayoutManager(this, 4) as RecyclerView.LayoutManager


        val intent = intent
//        purposeId = intent.extras?.getInt("purposeId")
//        purposeState = intent.extras?.getString("purposeState")
        purpose = intent.extras?.getParcelable("purpose")
        Log.e("purpose", purpose.toString())

        purpose?.let {
            binding.purposeName.text = it.purpose
            binding.purposeMemo.text = it.purposeMemo
            binding.startDate.text = it.startDate
            binding.endDate.text = it.endDate
            presenter.makeStampList(it.id)
        }

        when {
            purposeState.equals("S") -> {
                binding.stampBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccess))
                binding.buttonText.text = "목표 성공"

            }
            purposeState.equals("F") -> {
                binding.stampBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorFail))
                binding.buttonText.text = "목표 실패"

            }
            purposeState.equals("P") -> binding.stampBtn.setOnClickListener {
                val dialog = AlertDialog.Builder(this)
                        .setMessage("스탬프를 찍겠습니까?")
                        .setPositiveButton("확인") { dialog, which ->
                            presenter.addStamp()
                        }

                dialog.create().show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        if(purposeState.equals("P"))
            menuInflater.inflate(R.menu.setting_menu, menu)
        else
            menuInflater.inflate(R.menu.setting_menu_delete, menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.setting) {
            val purpose = purposeInfo?.run {
                PurposeInfo(idx,purposeName, purposeMemo, startDate, endDate, stampNum, purposeState)
            }

            val intent = Intent(this, PurposeModify::class.java)
            intent.putExtra("purposeInfo", purpose)
            startActivity(intent)
            finish()

        }else if(item?.itemId == R.id.deleteBtn){
            val dialog = AlertDialog.Builder(this)
                    .setMessage("목표를 삭제합니다.")
                    .setPositiveButton("확인") { dialog, which ->
                        presenter.deletePurpose()
                    }
                    .setNegativeButton("취소") { dialog, which ->
                        dialog.dismiss()
                    }

            dialog.create().show()

        }else if(item?.itemId == android.R.id.home){
            onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun getContext(): Context? {
        return this
    }

    override fun setAdapter(adapter: NewStampOnViewAdapter) {
        binding.recyclerVeiw.adapter = adapter
    }

    override fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>? {
        return binding.recyclerVeiw.adapter
    }

    override fun reflashListView() {
        binding.recyclerVeiw.adapter!!.notifyDataSetChanged()
    }

    override fun showCommonDialog(message: Int) {
        CommonFunction.showBasicDialog(this, message)
    }

    override fun successPurpose() {
        binding.stampBtn.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccess))
        binding.buttonText.text = "목표 성공"
        binding.stampBtn.isClickable = false
    }

    override fun deleteResult(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun setPurposeInfo(purpose: Purpose) {
        binding.purposeName.text = purpose.purposeName
        binding.purposeMemo.text = purpose.purposeMemo
        binding.startDate.text = purpose.startDate
        binding.endDate.text = purpose.endDate

        purposeInfo = purpose
    }
}