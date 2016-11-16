package com.unip.aps.aps_noticias.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.unip.aps.aps_noticias.app.ApsNoticiasApp;
import com.unip.aps.aps_noticias.ui.activity.BaseActivity;
import com.unip.aps.aps_noticias.model.UsuarioModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected UsuarioModel usuario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = ApsNoticiasApp.getInstance().getUsuario();
    }

    public void showProgressDialog(boolean isShow, String mensagem) {
        try {
            if (getActivity() instanceof BaseActivity) {
                getBaseActivity().showProgressDialog(isShow, mensagem);
            }
        } catch (Exception ex) {

        }
    }

    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    public void openActivity(Class<?> classOpen, String param, Object value) {
        try {
            getBaseActivity().openActivity(getActivity(), classOpen, param, value);
        } catch (Exception ex) {
        }
    }

    public void openActivity(Class<?> openActivty){
        getBaseActivity().openActivity(getActivity(), openActivty);
    }

    public void openActivityForResult(Class<?> openActivity, int requestCode){
        getBaseActivity().openActivityForResult(openActivity, requestCode);
    }

    public void openActivityForResult(Class<?> openActivity, String param, Object object, int requestCode){
        getBaseActivity().openActivityForResult(openActivity, param, object, requestCode);
    }

}
