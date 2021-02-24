package com.surehappiness.app.activity.successlist

import com.surehappiness.app.activity.successlist.adapter.NewSuccessListAdapter
import com.surehappiness.app.dto.Purpose
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Model constructor(private val requiredPresenter: Contract.RequiredPresenter){

    private val purposeCall = RetrofitNetwork.getInstance().purposeCall

    fun makeSuccessList(){

        val userInfoPref = requiredPresenter.getContext()!!.getSharedPreferences("userInfo", 0)
        val userIdx = userInfoPref?.getInt("userIdx", 0)!!

        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)

        if(networkInfo.isConnected){
            purposeCall.getPurposeSuccessList(userIdx)?.enqueue(object : Callback<List<Purpose>> {
                override fun onFailure(call: Call<List<Purpose>>?, t: Throwable?) {
                    requiredPresenter.showDialog("서버 연결이 원활하지 않습니다.")
                }

                override fun onResponse(call: Call<List<Purpose>>?, response: Response<List<Purpose>>?) {
                    // Log.i("list", response?.body().toString())
                    val responsePurpose = response!!.body()
                    var total = 0

                    for(purpose in responsePurpose!!){
                        total += purpose.stampNum
                    }
                    requiredPresenter.setTotalStamp(total)

                    val adapter = NewSuccessListAdapter(responsePurpose)
                    requiredPresenter.setAdapter(adapter)
                }
            })
        } else {
            requiredPresenter.showDialog("네트워크 연결 상태를 확인해주세요.")
        }

    }
}