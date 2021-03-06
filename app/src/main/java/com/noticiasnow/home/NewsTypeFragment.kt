package com.noticiasnow.home

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ricarlo.common.ui.base.BaseFragment
import br.com.ricarlo.common.util.ViewState
import com.noticiasnow.R
import com.noticiasnow.databinding.FragmentNewsTypeBinding
import com.noticiasnow.details.DetailsNewsActivity
import com.noticiasnow.details.DetailsViewModel
import com.noticiasnow.model.NewsModel
import org.koin.android.viewmodel.ext.android.viewModel

class NewsTypeFragment : BaseFragment<FragmentNewsTypeBinding>(), OnClickListener {

    private val _adapter by lazy { RecycleAdapter(this) }
    private val _type by lazy { arguments?.getString(EXTRA_TYPE) }

    private val viewModel: NewsTypeViewModel by viewModel()
    private val detailsViewModel: DetailsViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_news_type
    }

    override fun initView(savedInstanceState: Bundle?) {

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.setType(_type)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = _adapter
        }

        subscribeUI()
        viewModel.setType(_type)
    }

    override fun showLoading(message: String?) {
        binding.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefresh.isRefreshing = false
    }

    private fun subscribeUI() {
        viewModel.news.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                        showLoading(null)
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        _adapter.submitList(it.data)
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(it.error)
                    }
                }
            }
        )

        detailsViewModel.like.observe(
            viewLifecycleOwner,
            Observer {
                // TODO
            }
        )
    }

    override fun onClickItem(item: NewsModel) {
        openActivity(
            DetailsNewsActivity::class.java,
            bundleOf(
                DetailsNewsActivity.BUNDLE_NEWS to item
            )
        )
    }

    override fun onClickLike(item: NewsModel, like: Boolean) {
        detailsViewModel.createLike(like, item.id)
    }

    override fun onClickUpdate(item: NewsModel) {
        // TODO("Not yet implemented")
    }

    override fun onClickDelete(item: NewsModel) {
        // TODO("Not yet implemented")
    }

    companion object {
        private const val EXTRA_TYPE = "extra_type"
        fun getInstance(type: String): NewsTypeFragment {
            return NewsTypeFragment().apply {
                arguments = bundleOf(EXTRA_TYPE to type)
            }
        }
    }
}
