package com.surehappiness.app.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.surehappiness.app.R
import com.surehappiness.app.databinding.NewLayoutFindPwBinding
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.CommonFunction
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewFindPWFragment : Fragment() {
    lateinit var binding: NewLayoutFindPwBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_layout_find_pw, container, false)

        binding.nextBtn.setOnClickListener {
            findUserPw()
        }

        return binding.root
    }

    private fun findUserPw(){
        val userCall = RetrofitNetwork.getInstance().userCall
        val networkInfo = NetworkUtils.getNetworkInfo(context!!)

        if(!networkInfo .isConnected){
            CommonFunction.showBasicDialog(context!!, R.string.network_fail)
            return
        }

        val account = binding.userId.text.toString()
        val name = binding.userName.text.toString()
        val email = binding.userEmail.text.toString()

        val call = userCall.findUserPw(account, name, email)
        call.enqueue(object : Callback<Boolean>{
            override fun onResponse(call: Call<Boolean>?, response: Response<Boolean>?) {
                val result = response!!.body()

                if(result){
                    val intent = Intent(activity, NewPwUpdateActivity::class.java)
                    startActivity(intent)
//                    activity!!.finish()
                }else{
                    binding.notice.text = "* 등록된 계정이 없습니다.\n  정보를 다시 확인해주세요."
                }
            }

            override fun onFailure(call: Call<Boolean>?, t: Throwable?) {
                CommonFunction.showBasicDialog(context!!, R.string.server_connect_fail)
            }
        })
    }
}