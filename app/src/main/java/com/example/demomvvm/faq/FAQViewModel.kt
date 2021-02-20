package com.showaikh.driver.ui.help.faq

import androidx.lifecycle.MutableLiveData
import com.base.BAS.BaseNavigator
import com.example.demomvvm.AppClass
import com.example.demomvvm.FAQ
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.showaikh.driver.network.API_END_POINTS
import com.showaikh.driver.network.listeners.RetrofitResponseListener
import com.showaikh.driver.ui.base.BaseViewModel
import org.json.JSONObject

class FAQViewModel(app: AppClass) : BaseViewModel<BaseNavigator>(app) {

    var mArrFAQList = ArrayList<FAQ?>()
    var faqList = MutableLiveData<ArrayList<FAQ?>>()
//    var mMyEarning: MyEarnings? = null

    fun apiGetFaqList() {

        val params : HashMap<String,String> = HashMap()
        params["start"] = "0"
        params["limit"] = "10"
        params["device_type"] = "A"
        params["device_id"] = "drSdpY3mxpw:APA91bGyF30y6WXfkk4cAmo8bwwDK7ozfk07VlBDiG6UIXuUYHT9Tsv1mtJPMVfvQPmuoDtlR1QuCdfajXoAZPgyGR3GocCjeDsG_Npzsp9qEuublbgVIJQdTSSMReoFY6AGw5VP-d4M"
        NetworkCall.with(appContext)
             .setRequestParams(params)
            .setEndPoint(API_END_POINTS.GET_FAQ_LIST)
            .setResponseListener(object : RetrofitResponseListener {

                override fun onPreExecute() {
                    navigator?.showLoading()
                }

                override fun onSuccess(statusCode: Int, jsonObject: JSONObject, response: String) {

                    navigator?.hideLoading()
//                    userInfo = Gson().fromJson<UserInfo>(jsonObject.optString("user").toString(), UserInfo::class.java)
                 /*   mMyEarning = Gson().fromJson<MyEarnings>(
                        jsonObject.toString(),
                        object : TypeToken<MyEarnings>() {}.type
                    )*/
                    mArrFAQList = Gson().fromJson(jsonObject.optJSONArray("faq")!!.toString(), object : TypeToken<ArrayList<FAQ>>() {}.type)

                    faqList.value = mArrFAQList
                    navigator!!.storeDataInDb(mArrFAQList)
                    navigator!!.getRoomData()
                }

                override fun onError(statusCode: Int, messages: ArrayList<String>) {
                    navigator?.hideLoading()
                    navigator!!.showToast(messages[0])
                }

            }).makeCall()

    }

}