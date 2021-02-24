package com.surehappiness.app.activity.stampactivity

import com.surehappiness.app.R
import com.surehappiness.app.activity.model.NewPurposeInfo
import com.surehappiness.app.activity.stampactivity.adapter.NewStampOnViewAdapter
import com.surehappiness.app.dto.Purpose
import com.surehappiness.app.dto.Stamp
import com.surehappiness.app.retrofit.RetrofitNetwork
import com.surehappiness.app.utils.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Model constructor(private val requiredPresenter: Contract.RequiredPresenter) {

    private var offStampList = ArrayList<Stamp>()
    private var purposeId: Int? = null

    fun makeStampList(purposeId: Int){
        this.purposeId = purposeId

        val stampCall = RetrofitNetwork.getInstance().stampCall
        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)

        if(!networkInfo.isConnected){
            requiredPresenter.showCommonDialog(R.string.network_fail)
            return
        }

//        val call = stampCall.getStamp(this.purposeId!!)
//        call.enqueue(object: Callback<List<Stamp>> {
//            override fun onFailure(call: Call<List<Stamp>>?, t: Throwable?) {
//                requiredPresenter.showCommonDialog(R.string.server_connect_fail)
//            }
//
//            override fun onResponse(call: Call<List<Stamp>>?, response: Response<List<Stamp>>?) {
//                val stamps = response!!.body()
//
//                val adapter = NewStampOnViewAdapter(stamps)
//                requiredPresenter.setAdapter(adapter)
//
//                stamps[0].purpose.run {
//                    requiredPresenter.setPurposeInfo(this)
//                }
//
//                for(stamp in stamps){
//                    if(stamp.updateDate == ""){
//                        offStampList.add(stamp)
//                    }
//                }
//            }
//        })
    }

    fun addStamp(){
//        val stamp = Stamp()
//        val purpose = Purpose()
//        purpose.idx = offStampList[0].purpose.idx
//        stamp.position = offStampList[0].position
//        stamp.purpose = purpose
//
//        val stampCall = RetrofitNetwork.getInstance().stampCall
//        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)
//
//        if(networkInfo.isConnected){
//            val call = stampCall.createStamp(stamp)
//            call.enqueue(object: Callback<List<Stamp>> {
//                override fun onFailure(call: Call<List<Stamp>>?, t: Throwable?) {
//                    requiredPresenter.showDialog("서버 연결이 원활하지 않습니다.")
//                }
//
//                override fun onResponse(call: Call<List<Stamp>>?, response: Response<List<Stamp>>?) {
//                    val stamps = response!!.body()
//
//                    (requiredPresenter.getAdapter() as NewStampOnViewAdapter).setItems(stamps)
//                    requiredPresenter.reflashListView()
//
//                    offStampList.remove(offStampList[0])
//
//                    if(stamps[0].purpose.purposeState == "S")
//                        requiredPresenter.successPurpose()
//                }
//            })
//        }else{
//            requiredPresenter.showDialog("네트워크 연결 상태를 확인해주세요.")
//        }
    }

    fun deletePurpose(){
//        val purposeCall = RetrofitNetwork.getInstance().purposeCall
//        val networkInfo = NetworkUtils.getNetworkInfo(requiredPresenter.getContext()!!)
//
//        if(networkInfo.isConnected){
//            val call = purposeCall.deletePurpose(purposeId!!)
//            call.enqueue(object: Callback<Purpose> {
//                override fun onFailure(call: Call<Purpose>?, t: Throwable?) {
//                    requiredPresenter.showDialog("서버 연결이 원활하지 않습니다.")
//                }
//
//                override fun onResponse(call: Call<Purpose>?, response: Response<Purpose>?) {
//                    if (response!!.body().purposeName == "delete") {
//                        requiredPresenter.deleteResult("목표 삭제 완료")
//                    } else {
//                        requiredPresenter.deleteResult("목표 삭제 실패!")
//                    }
//                }
//            })
//        }else{
//            requiredPresenter.showDialog("네트워크 연결 상태를 확인해주세요.")
//        }
    }
}