package com.noticias_now.publish

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.lifecycle.Observer
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import br.com.ricarlo.common.util.extensions.getString
import br.com.ricarlo.common.util.extensions.showToast
import com.noticias_now.R
import com.noticias_now.databinding.ActivityPublishNewsBinding
import com.noticias_now.model.NewsModel
import org.koin.android.viewmodel.ext.android.viewModel

class PublishNewsActivity : BaseActivity<ActivityPublishNewsBinding>() {

    private val news by lazy { intent.getParcelableExtra(BUNDLE_NEWS) as? NewsModel }
    private val viewModel: PublishViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_publish_news
    }

    override fun initView(savedInstanceState: Bundle?) {
        setUpToolBar(getString(R.string.title_publicacao))

        binding.spTipoActivityPublicarNews.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selected = parent.selectedItemPosition
                val type = if (selected != 0) selected.toString() else ""
                viewModel.setType(type)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        binding.btActivityPublicarNews.setOnClickListener {
            viewModel.insertOrUpdateNews(
                title = binding.edTitleActivityPublicarNews.getString(),
                description = binding.edDescricaoActivityPublicarNews.getString()
            )
        }
        subscribeUI()
        viewModel.setNews(news)
    }

    private fun subscribeUI() {
        viewModel.result.observe(
            this,
            Observer {
                when (it) {
                    is ViewState.Loading -> {
                        showLoading(it.message)
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        showToast(it.data)
                        setResult(RESULT_OK)
                        finish()
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(it.error)
                    }
                }
            }
        )

        viewModel.news.observe(
            this,
            Observer {
                it?.let {
                    binding.edTitleActivityPublicarNews.setText(it.title)
                    binding.edDescricaoActivityPublicarNews.setText(it.description)
                    binding.spTipoActivityPublicarNews.setSelection(it.type.toInt())
                    setUpToolBar(getString(R.string.atualizar_news))
                    binding.btActivityPublicarNews.text = getString(R.string.atualizar)
                }
            }
        )
    }

    companion object {
        const val BUNDLE_NEWS = "bundle_news"
        const val REQUEST_PUBLISH = 12321
    }
}
