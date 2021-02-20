package com.showaikh.driver.ui.authentication

import com.base.BAS.BaseNavigator
import com.example.demomvvm.FAQ


interface LoginNavigator : BaseNavigator {
    fun openCompleteProfileActivity()
    fun openMainActivity()
    fun openMobileVerificationActivity(userInfo: FAQ)

}