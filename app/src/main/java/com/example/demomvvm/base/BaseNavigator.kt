package com.base.BAS

import com.example.demomvvm.FAQ

interface BaseNavigator {
    fun storeDataInDb(arrList : ArrayList<FAQ?>)
    fun getRoomData()
    fun showLoading()
    fun hideLoading()
    fun showToast(msg: String)
    fun showSnackBar(message: String, isError: Boolean)
    fun showAlert(title: String, msg: String?)
    fun gotoLogin()
    fun finishActivity()
}
