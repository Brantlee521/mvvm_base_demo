package com.showaikh.driver.ui.help.faq

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.BAS.BaseNavigator
import com.example.demomvvm.BR
import com.example.demomvvm.FAQ
import com.example.demomvvm.R
import com.example.demomvvm.ViewModelProviderFactory
import com.example.demomvvm.databinding.ActivityFaqBinding
import com.example.demomvvm.faq.FAQListAdapter
import com.example.demomvvm.roomDataBase.Dais
import com.example.demomvvm.roomDataBase.DaisViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.showaikh.driver.ui.base.BaseActivity

class FAQActivity : BaseActivity<ActivityFaqBinding, FAQViewModel>(), BaseNavigator {
    private lateinit var daisViewModel: DaisViewModel

    override val viewModel: FAQViewModel
        get() = ViewModelProvider(
            this,
            ViewModelProviderFactory<FAQViewModel>(FAQViewModel(app))
        ).get(FAQViewModel::class.java)
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_faq

    var adapter: FAQListAdapter? = null

    override fun initialization(savedInstance: Bundle?) {

        viewModel.navigator = this
        daisViewModel = ViewModelProviders.of(this).get(DaisViewModel::class.java)

        /*  setUpToolbarWithBlackBackArrow(
              viewDataBinding!!.toolbar.toolbar, getString(R.string.lbl_faq),
              true
          )*/

        viewModel.apiGetFaqList()

        setUpRecyclerView()

        populateData()
    }

    private fun populateData() {
        viewModel.faqList.observe(this, Observer {
            adapter!!.list = it
            if (it.size > 0) {
                viewDataBinding!!.rvFaqList.visibility = View.VISIBLE
                viewDataBinding!!.tvNoDataFound.visibility = View.GONE
            } else {
                viewDataBinding!!.rvFaqList.visibility = View.GONE
                viewDataBinding!!.tvNoDataFound.visibility = View.VISIBLE
            }
        })
    }

    private fun setUpRecyclerView() {
        viewDataBinding!!.rvFaqList.layoutManager = LinearLayoutManager(this@FAQActivity)
        adapter = FAQListAdapter(this@FAQActivity, viewDataBinding!!.rvFaqList)
        viewDataBinding!!.rvFaqList.adapter = adapter
    }

    companion object {
        fun newIntent(context: Context, isFinish: Boolean): Intent {
            val intent = Intent(context, FAQActivity::class.java)
            if (isFinish)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

            return intent
        }
    }

    override fun storeDataInDb(arrList: ArrayList<FAQ?>) {
        val dais =
            Dais(
                getString(R.string.newsListOfflineData),
                Gson().toJson(arrList)
            )
        try {
            daisViewModel.insert(dais)
        } catch (e: Exception) {
        }
    }

    override fun getRoomData() {
        daisViewModel.getJsonData(getString(R.string.newsListOfflineData))
            .observe(this, Observer<String> { s ->
                if (s != null && !s.equals("", ignoreCase = true)) {
                    val turnsType = object : TypeToken<ArrayList<FAQ?>>() {}.type
                    val arrayList = Gson().fromJson<ArrayList<FAQ?>>(s, turnsType)

                    Log.e("mytag", "ROOM DATABASE:::::::::::::" + Gson().toJson(arrayList))

                }
            })
    }

    override fun showSnackBar(message: String, isError: Boolean) {
        TODO("Not yet implemented")
    }

    override fun showAlert(title: String, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun gotoLogin() {
        TODO("Not yet implemented")
    }



}