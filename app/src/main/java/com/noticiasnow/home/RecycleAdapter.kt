package com.noticiasnow.home

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import br.com.ricarlo.common.ui.base.adapter.BaseAdapter
import br.com.ricarlo.common.ui.base.adapter.BaseViewHolder
import com.noticiasnow.BR
import com.noticiasnow.R
import com.noticiasnow.model.NewsModel

/**
 * Created by ricarlo on 12/11/2016.
 */
class RecycleAdapter(
    private val listener: OnClickListener
) : BaseAdapter<NewsModel, RecycleAdapter.ViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<NewsModel>() {
        override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel): Boolean {
            // override equals
            return oldItem == newItem
        }
    }

    sealed class ViewHolder(view: ViewDataBinding) : BaseViewHolder<NewsModel>(view) {
        class News(private val binding: ViewDataBinding, private val listener: OnClickListener) : ViewHolder(binding) {
            override fun onBind(item: NewsModel, position: Int) {
                binding.setVariable(BR.item, item)
                binding.setVariable(BR.listener, listener)
                binding.executePendingBindings()
            }
        }
    }

    override fun getItemView(viewType: Int): Int {
        return R.layout.item_card_view
    }

    override fun instantiateViewHolder(view: ViewDataBinding, viewType: Int): ViewHolder {
        return ViewHolder.News(view, listener)
    }
}

interface OnClickListener {
    fun onClickItem(item: NewsModel)
    fun onClickUpdate(item: NewsModel)
    fun onClickDelete(item: NewsModel)
    fun onClickLike(item: NewsModel, like: Boolean)
}
