package com.surehappiness.app.activity.purposelist

import android.content.Context
import android.util.Log
import com.surehappiness.app.R
import com.surehappiness.app.activity.NewPurposeList
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.activity.purposelist.adapter.NewPurposeListAdapter
import com.surehappiness.app.dto.Purpose
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.CommonFunction
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model constructor(private val requiredPresenter: Contract.RequiredPresenter) {
    private var userId: Int? = null

    fun getUserInfoPreference(){
        val userInfoPref = requiredPresenter.getContext()!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        userId = userInfoPref.getInt("userId", 0)
        val userName = userInfoPref.getString("userName", null)

        requiredPresenter.setUserInfo(userName)
    }

    fun makePurposeList(){
        val purposeCall = RetrofitNetwork.getInstance().purposeCall
        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)

        if(!networkInfo.isConnected){
            requiredPresenter.showDialog(R.string.network_fail)
            return
        }

        val call = purposeCall.getPurposeList("ALL",userId!!)
        call.enqueue(object: Callback<List<NewPurposeInfo>>{
            override fun onResponse(call: Call<List<NewPurposeInfo>>?, response: Response<List<NewPurposeInfo>>?) {
                if (response!!.code() != 200) {
                    requiredPresenter.showDialog(R.string.error)
                    return
                }

                val items = response.body()
                val adapter = NewPurposeListAdapter(items!!)
                requiredPresenter.setAdapter(adapter)
            }

            override fun onFailure(call: Call<List<NewPurposeInfo>>?, t: Throwable?) {
                requiredPresenter.showDialog(R.string.server_connect_fail)
            }
        })

    }


    fun makeSearchList(query: String?) {
        val purposeCall = RetrofitNetwork.getInstance().purposeCall
        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)

        val requestPurpose = Purpose()
        requestPurpose.purposeName = query

        if (!networkInfo.isConnected) {
            requiredPresenter.showDialog(R.string.network_fail)
            return
        }

        val call = purposeCall.searchPurpose(userId!!, requestPurpose)
        call.enqueue(object : Callback<List<Purpose>> {
            override fun onResponse(call: Call<List<Purpose>>?, response: Response<List<Purpose>>?) {
                val items = response!!.body()

//                val adapter = NewPurposeListAdapter(items!!)
//                requiredPresenter.setAdapter(adapter)
            }

            override fun onFailure(call: Call<List<Purpose>>?, t: Throwable?) {
//                requiredPresenter.showDialog("서버 연결이 원활하지 않습니다.")
            }
        })

    }


}