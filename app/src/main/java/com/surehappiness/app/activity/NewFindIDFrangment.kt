package com.surehappiness.app.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.surehappiness.app.R
import com.surehappiness.app.databinding.NewLayoutFindIdBinding
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.CommonFunction
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewFindIDFrangment: Fragment() {
    private  lateinit var binding: NewLayoutFindIdBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_layout_find_id, container, false)

        binding.findBtn.setOnClickListener {
            val checkEmail = checkEmailPattern()
            if(TextUtils.isEmpty(binding.userName.text.toString())){
                binding.notice.text = "* 이름을 입력하세요."
            }else if(TextUtils.isEmpty(binding.userEmail.text.toString())){
                binding.notice.text = "* 이메일 주소를 입력하세요."
            }else if(!checkEmail){
                binding.notice.text = "* 이메일 형식이 올바르지 않습니다."
            }else{
                findUserId()
            }
        }

        return binding.root

    }

    private fun checkEmailPattern(): Boolean{
        val regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$" as Regex
        return binding.userEmail.text.toString().matches(regex)
    }

    private fun findUserId(){
        val userCall = RetrofitNetwork.getInstance().userCall
        val networkInfo = NetworkUtils.getNetworkInfo(context!!)

        if(networkInfo == null){
            CommonFunction.showBasicDialog(context!!, R.string.network_fail)
            return
        }

        val userName = binding.userName.text.toString()
        val userEmail = binding.userEmail.text.toString()

        val call = userCall.findUserId(userName, userEmail)
        call.enqueue(object: Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                if(response!!.code() != 200){
                    val errCode = CommonFunction.errJsonParsing(response.errorBody())

                    if (errCode == null) {
                        CommonFunction.showBasicDialog(context!!, R.string.error)
                        return
                    }

                    when (errCode) {
                        "EU003" -> binding.notice.text = "* 등록된 계정이 없습니다.\n  이름과 이메일을 다시 확인해주세요."

                        "EU004" -> binding.notice.text = "* 메일 전송 실패\n  관리자에게 문의하세요."

                        else -> CommonFunction.showBasicDialog(context!!, R.string.error)
                    }
                    return
                }

                CommonFunction.showBasicBtnDialog(activity!!, "아이디를 전송했습니다.\n 이메일을 확인해주세요.")
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                CommonFunction.showBasicDialog(context!!, R.string.server_connect_fail)
            }
        })
    }
}