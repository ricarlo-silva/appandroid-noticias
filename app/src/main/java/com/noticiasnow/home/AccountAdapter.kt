package com.noticiasnow.home

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import br.com.ricarlo.common.ui.base.adapter.BaseAdapter
import br.com.ricarlo.common.ui.base.adapter.BaseViewHolder
import com.noticiasnow.BR
import com.noticiasnow.R

class AccountAdapter(
    private val listener: OnClickListener
) : BaseAdapter<Int, AccountAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            // override equals
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: ViewDataBinding) : BaseViewHolder<Int>(binding) {
        override fun onBind(item: Int, position: Int) {
            binding.setVariable(BR.item, binding.root.context.getString(item))
            binding.setVariable(BR.index, item)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    override fun getItemView(viewType: Int): Int {
        return R.layout.item_account
    }

    override fun instantiateViewHolder(view: ViewDataBinding, viewType: Int): ViewHolder {
        return ViewHolder(view)
    }

    interface OnClickListener {
        fun onClickItem(item: Int)
    }
}
