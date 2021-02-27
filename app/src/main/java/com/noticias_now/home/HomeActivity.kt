package com.noticias_now.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import com.noticias_now.R
import com.noticias_now.databinding.ActivityHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initView(savedInstanceState: Bundle?) {

        setSupportActionBar(binding.toolbar)

        subscribeUI()

        viewModel.get()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, AccountActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.bt_nav_header_edit_perfil -> {
//                openActivity(EditProfileActivity::class.java, null, null)
//            }
//            R.id.tv_nav_my_news -> {
//                openActivity(UserNewsActivity::class.java, null, null)
//            }
//            R.id.tv_nav_logout -> {
// //                logout()
//            }
//        }
//    }

    private fun subscribeUI() {

        viewModel.types.observe(
            this,
            Observer { result ->
                when (result) {
                    is ViewState.Loading -> {
                        showLoading(getString(R.string.loading))
                    }
                    is ViewState.Success -> {
                        hideLoading()
                        binding.content.tabLayout.setupWithViewPager(binding.content.pager)

                        val list = result.data.associateBy({ it.name }, { NewsTypeFragment.getInstance(it.id) })
                        val adapter = PagerAdapter(list, supportFragmentManager)

                        binding.content.pager.adapter = adapter
                    }
                    is ViewState.Error -> {
                        hideLoading()
                        handlerError(result.error)
                    }
                }
            }
        )
    }
}
