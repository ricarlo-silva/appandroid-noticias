package com.noticias_now.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.noticias_now.R;
import com.noticias_now.app.ApsNoticiasApp;
import com.noticias_now.model.UsuarioModel;

import java.io.Serializable;

/**
 * Created by ricarlo on 10/11/2016.
 */

public class BaseActivity extends AppCompatActivity {


    private MaterialDialog progressDialog;
    protected boolean runningBackground = false;
    protected static UsuarioModel usuario;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProgressDialog();
        usuario = ApsNoticiasApp.getInstance().getUsuario();

    }

    @Override
    protected void onResume() {
        super.onResume();
        runningBackground = false;
        ApsNoticiasApp.currentActivity = this;
        usuario = ApsNoticiasApp.getInstance().getUsuario();
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningBackground = true;
    }

    public void openActivity(Context context, Class<?> classOpen) {
        Intent intent = new Intent();
        intent.setClass(context, classOpen);
        startActivity(intent);
    }

    public void openActivity(Context context, Class<?> classOpen, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, classOpen);
        startActivityForResult(intent, requestCode);
    }

    public void openActivity(Context context, Class<?> openActivity, String extraName, Object extraValue) {
        Intent intent = new Intent();
        intent.setClass(context, openActivity);
        intent.putExtra(extraName, (Serializable) extraValue);
        startActivity(intent);
    }

    public void openActivity(Context context, Class<?> openActivity, Intent intentBundle) {
        Intent intent = new Intent();
        intent.setClass(context, openActivity);
        if (intentBundle != null && intentBundle.getExtras() != null) {
            intent.putExtras(intentBundle.getExtras());
        }
        startActivity(intent);
    }

    public void openActivityForResult(Class<?> openActivity, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, openActivity);
        startActivityForResult(intent, requestCode);
    }

    public void openActivityForResult(Class<?> openActivity, String param, Object obj, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, openActivity);
        intent.putExtra(param, (Serializable) obj);
        startActivityForResult(intent, requestCode);
    }

    private void createProgressDialog() {
        progressDialog = new MaterialDialog.Builder(this)
                .content("Aguarde...")
                .cancelable(false)
                .progress(true, 0)
                .build();
    }

    public void showProgressDialog(boolean isShow, String mensagem) {
        if (progressDialog != null) {
            if (!TextUtils.isEmpty(mensagem)) {
                progressDialog.setContent(mensagem);
            } else {
                progressDialog.setContent("Aguarde...");
            }
            if (isShow && !runningBackground) {
                progressDialog.show();
            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }
    }

    protected void setUpToolBar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar == null)
            return;

        TextView tv_title = (TextView) toolbar.findViewById(R.id.title_toolbar);
        tv_title.setText(title);

        setSupportActionBar(toolbar);

    }

    protected void shareNoticia(String title, String url_pub) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        share.putExtra(Intent.EXTRA_TEXT, title + "\n\n" + url_pub);
        startActivity(share);
    }
}