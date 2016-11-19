package com.unip.aps.aps_noticias.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.unip.aps.aps_noticias.R;
import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.ui.fragment.PagerAdapter;


public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private PagerAdapter adapter;

    // NavigationView
    private ImageView iv_foto;
    private TextView tv_nome;
    private TextView tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        initNavigationView(navigationView);
        setDataNavigationView();


        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(adapter.getTabView(i));
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataNavigationView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigationView(NavigationView nav){

        // Nav Header
        iv_foto = (ImageView) nav.findViewById(R.id.iv_nav_header_photo);
        tv_nome = (TextView) nav.findViewById(R.id.tv_nav_header_name);
        tv_email = (TextView) nav.findViewById(R.id.tv_nav_header_email);

        Button bt_edit_perfil = (Button) nav.findViewById(R.id.bt_nav_header_edit_perfil);
        bt_edit_perfil.setOnClickListener(this);

        TextView tv_home = (TextView) nav.findViewById(R.id.tv_nav_home);
        TextView tv_logout = (TextView) nav.findViewById(R.id.tv_nav_logout);

        tv_home.setOnClickListener(this);
        tv_logout.setOnClickListener(this);

    }

    private void setDataNavigationView(){
        tv_nome.setText(usuario.getName());
        tv_email.setText(usuario.getEmail());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.bt_nav_header_edit_perfil:
                openActivity(this, EditarPerfilActivity.class);
                hideNavigationView();
                break;
            case R.id.tv_nav_home:
                hideNavigationView();
                break;
            case R.id.tv_nav_logout:
                hideNavigationView();
                logout();
                break;
        }
    }

    private void logout(){
        new MaterialDialog.Builder(this)
                .title(R.string.app_name)
                .backgroundColorRes(R.color.indigo_200)
                .content(R.string.sair)
                .positiveText(R.string.sim)
                .negativeText(R.string.nao)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        ApsNoticiasApp.getInstance().doLogout();
                        openActivity(HomeActivity.this, LoginActivity.class);
                        finish();
                    }
                })
                .show();
    }

    private void hideNavigationView(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
}
