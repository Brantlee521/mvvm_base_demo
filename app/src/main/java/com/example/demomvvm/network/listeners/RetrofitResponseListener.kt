package com.showaikh.driver.network.listeners

import org.json.JSONObject

import java.util.ArrayList


interface RetrofitResponseListener {
    fun onPreExecute()
    fun onSuccess(statusCode: Int, jsonObject: JSONObject, response: String)
    fun onError(statusCode: Int, messages: ArrayList<String>)
}
