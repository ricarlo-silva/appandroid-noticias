package com.noticiasnow.list

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import com.afollestad.materialdialogs.MaterialDialog
import com.noticiasnow.R
import com.noticiasnow.databinding.ActivityUserNewsBinding
import com.noticiasnow.details.DetailsNewsActivity
import com.noticiasnow.home.OnClickListener
import com.noticiasnow.home.RecycleAdapter
import com.noticiasnow.model.NewsModel
import com.noticiasnow.publish.PublishNewsActivity
import org.koin.android.viewmodel.ext.android.viewModel

class UserNewsActivity : BaseActivity<ActivityUserNewsBinding>(), OnClickListener {

    private val _adapter by lazy { RecycleAdapter(this) }

    private val viewModel: UserNewsViewModel by viewModel()

    override fun onClickItem(item: NewsModel) {
        openActivity(
            DetailsNewsActivity::class.java,
            bundleOf(
                DetailsNewsActivity.BUNDLE_NEWS to item
            )
        )
    }

    override fun onClickLike(item: NewsModel, like: Boolean) {
        // TODO("Not yet implemented")
    }

    override fun onClickUpdate(item: NewsModel) {
        openActivity(
            PublishNewsActivity::class.java,
            bundleOf(PublishNewsActivity.BUNDLE_NEWS to item),
            PublishNewsActivity.REQUEST_PUBLISH
        )
    }

    override fun onClickDelete(item: NewsModel) {
        dialogDeleteNews(item)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_user_news
    }

    override fun initView(savedInstanceState: Bundle?) {
        setUpToolBar(getString(R.string.title_my_publish))

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNews()
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserNewsActivity)
            adapter = _adapter
        }

        subscribeUI()
        viewModel.getNews()
    }

    override fun showLoading(message: String?) {
        binding.swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefresh.isRefreshing = false
    }

    private fun subscribeUI() {
        viewModel.news.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                        showLoading(getString(R.string.loading))
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

        viewModel.delete.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                        showLoading(getString(R.string.loading))
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        _adapter.currentList.remove(it.data)
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(it.error)
                    }
                }
            }
        )
    }

    private fun dialogDeleteNews(news: NewsModel) {
        MaterialDialog.Builder(this)
            .title(R.string.app_name)
            .content(R.string.deletar_news)
            .positiveText(R.string.sim)
            .negativeText(R.string.nao)
            .onPositive { _, _ ->
                viewModel.deleteNews(news)
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PublishNewsActivity.REQUEST_PUBLISH && resultCode == RESULT_OK) {
            viewModel.getNews()
        }
    }
}
