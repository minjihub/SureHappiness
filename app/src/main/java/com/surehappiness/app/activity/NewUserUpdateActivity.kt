package com.surehappiness.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.surehappiness.app.R
import com.surehappiness.app.databinding.NewLayoutProfileModifyBinding

class NewUserUpdateActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: NewLayoutProfileModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_layout_profile_modify) //이거 없어도되나

        binding = DataBindingUtil.setContentView(this, R.layout.new_layout_profile_modify)

        //userPref 에서 값 꺼내와서 셋팅

        binding.userPw.setOnClickListener(this)
        binding.userDeleteBtn.setOnClickListener(this)
        binding.saveBtn.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.user_pw ->{
                val intent = Intent(this, PwCheckAndUpdateActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.userDeleteBtn ->{
                val intent = Intent(this, PwCheckAndDeleteActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.saveBtn ->{
                //이메일 정규식 확인 추가할 것

                userProfileUpdate()
            }

        }
    }

    private fun userProfileUpdate(){

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home){
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}