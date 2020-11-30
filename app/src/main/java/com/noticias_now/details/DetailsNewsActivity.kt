package com.noticias_now.details

import android.os.Bundle
import br.com.ricarlo.common.util.ViewState
import com.noticias_now.R
import com.noticias_now.databinding.ActivityDetailsNewsBinding
import com.noticias_now.model.NewsModel
import br.com.ricarlo.common.ui.base.BaseActivity
import org.koin.android.viewmodel.ext.android.viewModel

class DetailsNewsActivity : BaseActivity<ActivityDetailsNewsBinding>() {

    private val news by lazy { intent.getParcelableExtra(BUNDLE_NEWS) as? NewsModel }

    private val viewModel: DetailsViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_details_news
    }

    override fun initView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { share(news?.title.orEmpty(), news?.description.orEmpty()) }
        binding.content.ivLikeYes.setOnClickListener { viewModel.createLike(true, news?.id) }
        binding.content.ivLikeNo.setOnClickListener { viewModel.createLike(false, news?.id) }

        subscribeUI()
        viewModel.getNewsById(news)
    }

    private fun subscribeUI() {
        viewModel.news.observe(this) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    hideLoading()
                    title = it.data.title
                    binding.content.tvDescription.text = it.data.description
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        }

        viewModel.like.observe(this) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    hideLoading()
                    binding.content.tvLikeYes.text = "${it.data.first}"
                    binding.content.tvLikeNo.text = "${it.data.second}"

                    binding.content.ivLikeYes.isSelected = it.data.third
                    binding.content.ivLikeNo.isSelected = it.data.third
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        }
    }

    companion object {
        const val BUNDLE_NEWS = "bundle_news"
    }
}