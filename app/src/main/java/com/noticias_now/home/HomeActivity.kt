package com.noticias_now.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import br.com.ricarlo.common.ui.base.BaseActivity
import br.com.ricarlo.common.util.ViewState
import com.afollestad.materialdialogs.MaterialDialog
import com.noticias_now.R
import com.noticias_now.account.update.EditProfileActivity
import com.noticias_now.databinding.ActivityHomeBinding
import com.noticias_now.list.UserNewsActivity
import com.noticias_now.login.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity<ActivityHomeBinding>(), View.OnClickListener {

    private val viewModel: HomeViewModel by viewModel()

    override fun getLayoutRes(): Int {
        return R.layout.activity_home
    }

    override fun initView(savedInstanceState: Bundle?) {

        setSupportActionBar(binding.home.toolbar)
        val toggle = ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.home.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        subscribeUI()

        viewModel.get()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

//    private fun initNavigationView(nav: NavigationView) {
//        // Nav Header
//        iv_foto = nav.findViewById(R.id.iv_nav_header_photo)
//        tv_nome = nav.findViewById(R.id.tv_nav_header_name)
//        tv_email = nav.findViewById(R.id.tv_nav_header_email)
//        val bt_edit_perfil = nav.findViewById<Button>(R.id.bt_nav_header_edit_perfil)
//        bt_edit_perfil.setOnClickListener(this)
//        val tv_home = nav.findViewById<TextView>(R.id.tv_nav_home)
//        val tv_my_news = nav.findViewById<TextView>(R.id.tv_nav_my_news)
//        val tv_logout = nav.findViewById<TextView>(R.id.tv_nav_logout)
//        tv_home.setOnClickListener(this)
//        tv_my_news.setOnClickListener(this)
//        tv_logout.setOnClickListener(this)
//    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_nav_header_edit_perfil -> {
                openActivity(EditProfileActivity::class.java, null, null)
                hideNavigationView()
            }
            R.id.tv_nav_home -> hideNavigationView()
            R.id.tv_nav_my_news -> {
                openActivity(UserNewsActivity::class.java, null, null)
                hideNavigationView()
            }
            R.id.tv_nav_logout -> {
                hideNavigationView()
                logout()
            }
        }
    }

    private fun subscribeUI() {

        viewModel.types.observe(this) {
            when (it) {
                is ViewState.Loading -> {
                    showLoading(getString(R.string.loading))
                }
                is ViewState.Success -> {
                    hideLoading()
                    binding.home.content.tabLayout.setupWithViewPager(binding.home.content.pager)

//                    val list = linkedMapOf(
//                            "poluição do ar" to NewsTypeFragment.getInstance(TypeNews.POLLUTION.value),
//                            "trânsito" to NewsTypeFragment.getInstance(TypeNews.TRAFFIC.value),
//                            "desmatamento" to NewsTypeFragment.getInstance(TypeNews.DEFORESTATION.value)
//                    )
                    val list = it.data.associateBy({ it.name }, { NewsTypeFragment.getInstance(it.id) })


                    val adapter = PagerAdapter(list, supportFragmentManager)

                    binding.home.content.pager.adapter = adapter
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        }

        viewModel.logout.observe(this) {
            when (it) {
                is ViewState.Loading -> {

                }
                is ViewState.Success -> {
                    hideLoading()
                    openActivity(LoginActivity::class.java, null, null)
                    finish()
                }
                is ViewState.Error -> {
                    hideLoading()
                    handlerError(it.error)
                }
            }
        }

        viewModel.user.observe(this) {
            // TODO check if update after edit profile
            binding.navHeader.tvNavHeaderName.text = it.name
            binding.navHeader.tvNavHeaderEmail.text = it.email
        }
    }

    private fun logout() {
        MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .content(R.string.sair)
                .positiveText(R.string.sim)
                .negativeText(R.string.nao)
                .onPositive { _, _ ->
                    viewModel.logout()
                }
                .show()
    }

    private fun hideNavigationView() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }
}