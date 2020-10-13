package com.noticias_now.ui.fragment;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.noticias_now.R;

/**
 * Created by ricarlo on 12/11/2016.
 */

public class PagerAdapter extends FragmentPagerAdapter {


    private Context context;
    private String tabTitles[] = new String[] { "poluição do ar", "trânsito", "desmatamento" };

    public View getTabView(int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        return v;
    }

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PoluicaoArFragment();
            case 1:
                return new TransitoFragment();
            case 2:
                return new DesmatamentoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
