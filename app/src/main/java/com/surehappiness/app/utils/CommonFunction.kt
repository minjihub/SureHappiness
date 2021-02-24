package com.surehappiness.app.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

class CommonFunction {

    companion object {

        fun showBasicDialog(context: Context, msg: String){
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage(msg).show()
        }

        fun showBasicDialog(context: Context, msg: Int){
            val dialog = AlertDialog.Builder(context)
            val message = context.resources.getString(msg)
            dialog.setMessage(message).show()
        }

        fun showBasicBtnDialog(activity: Activity, msg: String){
            val dialog = AlertDialog.Builder(activity)
                    .setMessage(msg)
                    .setCancelable(false)
                    .setPositiveButton("확인", object: DialogInterface.OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            activity.finish()
                        }
                    })
            dialog.show()
        }

        fun errJsonParsing(errBody: ResponseBody): String?{

            var errCode: String? = null

            try {
                val errJson = JSONObject(errBody.string())
                errCode = errJson.getString("result")
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            return  errCode
        }
    }
}