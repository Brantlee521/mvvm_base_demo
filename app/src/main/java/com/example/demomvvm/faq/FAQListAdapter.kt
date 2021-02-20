package com.example.demomvvm.faq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demomvvm.FAQ
import com.example.demomvvm.databinding.ItemProgressbarLayoutBinding
import com.example.demomvvm.databinding.RowFaqsItemBinding

import com.showaikh.driver.ui.base.BaseRecyclerViewAdapter
import com.showaikh.driver.ui.base.BaseViewHolder

class FAQListAdapter( val context: Context, rv: RecyclerView) : BaseRecyclerViewAdapter<BaseViewHolder, FAQ>(rv) {
    private val viewItem = 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == viewItem) {
            val itemBinding = RowFaqsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MyViewHolder(itemBinding)
        } else {
            val isLoadingBinding = ItemProgressbarLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LoadingView(isLoadingBinding)
        }
    }

    inner class MyViewHolder(private val itemBinding: RowFaqsItemBinding?) :
        BaseViewHolder(itemBinding!!.root) {

        override fun onBind(position: Int) {
            val model = list[position]
            itemBinding!!.llMain.tag = 0
            itemBinding.tvQuestion.text = model?.question
            itemBinding.tvAnswer.text = model?.answer

            itemBinding.llMain.setOnClickListener {
                if (itemBinding.llMain.tag == 0) {
                    itemBinding.llMain.tag = 1
                    itemBinding.tvAnswer.visibility = View.VISIBLE
                } else {
                    itemBinding.llMain.tag = 0
                    itemBinding.tvAnswer.visibility = View.GONE
                }
            }
        }

    }

    inner class LoadingView(loadingBinding: ItemProgressbarLayoutBinding?) :
        BaseViewHolder(loadingBinding!!.root) {
        override fun onBind(position: Int) {

        }

    }

    override fun getItemViewType(position: Int): Int {
        val viewProgress = 0
        val model = list[position]
        return if (model != null) viewItem else viewProgress
    }
}