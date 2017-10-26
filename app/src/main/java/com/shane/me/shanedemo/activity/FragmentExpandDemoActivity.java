package com.shane.me.shanedemo.activity;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.RadioGroup;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.fragment.ServerFragment;


/**
 * Created by luckyshane on 2017/10/25.
 */

public class FragmentExpandDemoActivity extends Activity {

    private Fragment[] fragments = new Fragment[3];
    private int fragmentType = ServerFragment.TYPE_RECOMMEND;

    private Fragment curFragment;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_expandable);
        initView();
    }

    private void initView() {
        ((RadioGroup)findViewById(R.id.server_tab)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.recommend_radio_btn:
                        fragmentType = ServerFragment.TYPE_RECOMMEND;
                        changeFragment(0);
                        break;
                    case R.id.all_radio_btn:
                        fragmentType = ServerFragment.TYPE_ALL;
                        changeFragment(1);
                        break;
                    case R.id.favourite_radio_btn:
                        fragmentType = ServerFragment.TYPE_FAVOURITE;
                        changeFragment(2);
                        break;
                }
            }
        });
        findViewById(R.id.recommend_radio_btn).performClick();  // 选择第一个
    }

    private void changeFragment(int index) {
        Fragment fragment = fragments[index];
        if (fragment == null) {
            fragment = ServerFragment.newInstance(fragmentType);
            fragments[index] = fragment;
        }
        if (curFragment != fragment) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (!fragment.isAdded()) {
                transaction.add(R.id.server_container, fragment);
            }
            for (Fragment tmpFragment : fragments) {
                if (tmpFragment != null) {
                    if (tmpFragment == fragment) {
                        transaction.show(tmpFragment);
                    } else {
                        transaction.hide(tmpFragment);
                    }
                }
            }
            transaction.commit();
            curFragment = fragment;
        }
    }






}
