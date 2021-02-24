package com.surehappiness.app.activity

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.google.gson.JsonObject
import com.surehappiness.app.R
import com.surehappiness.app.activity.model.UserInfo
import com.surehappiness.app.databinding.NewLayoutPwUpdateBinding
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.CommonFunction
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPwUpdateActivity : AppCompatActivity() {
    lateinit var binding: NewLayoutPwUpdateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.new_layout_pw_update)
        binding = DataBindingUtil.setContentView(this, R.layout.new_layout_pw_update)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(true)

        val userName = "전 화면에서 받아오기"
        binding.info.text = "$userName 님, 새로운 비밀번호를 입력해주세요."

        binding.saveBtn.setOnClickListener {
            val pw = binding.userPw.text.toString()
            val pwCheck = binding.userPw2.text.toString()

            if(TextUtils.isEmpty(pw) || TextUtils.isEmpty(pwCheck)){
                binding.notice.text = "* 비밀번호를 입력하세요."
                return@setOnClickListener //이게 뭐지
            }

            if(pw == pwCheck){
                binding.notice.text = "* 비밀번호가 일치하지 않습니다."
                return@setOnClickListener
            }

            updatePw()
        }
    }

    private fun updatePw(){
        val userCall = RetrofitNetwork.getInstance().userCall
        val networkInfo = NetworkUtils.getNetworkInfo(this)

        if(!networkInfo.isConnected){
            CommonFunction.showBasicDialog(this, R.string.network_fail)
            return
        }

        val userInfo = JsonObject()
        //userInfo.add("id", )
        userInfo.addProperty("password", binding.userPw.text.toString())

        val call = userCall.userInfoUpdate(userInfo)
        call.enqueue(object : Callback<UserInfo>{
            override fun onResponse(call: Call<UserInfo>?, response: Response<UserInfo>?) {
                if(response!!.code() != 200){
                    val errCode = CommonFunction.errJsonParsing(response.errorBody())

                    if (errCode == null) {
                        CommonFunction.showBasicDialog(this@NewPwUpdateActivity, R.string.error)
                        return
                    }

                    when (errCode) {
                        "EC004" -> binding.notice.text = "* 등록된 계정이 없습니다.\n  관리자에게 문의하세요."

                        else -> CommonFunction.showBasicDialog(this@NewPwUpdateActivity, R.string.error)
                    }
                    return
                }

                //user 정보 pref에 저장
                CommonFunction.showBasicBtnDialog(this@NewPwUpdateActivity, "비밀번호가 변경되었습니다.")
            }

            override fun onFailure(call: Call<UserInfo>?, t: Throwable?) {
                CommonFunction.showBasicDialog(this@NewPwUpdateActivity, R.string.server_connect_fail)
            }

        })
    }
}