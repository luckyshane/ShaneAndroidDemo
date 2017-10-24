package com.shane.me.shanedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.activity.DrawerLayoutDemoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luckyshane on 2017/10/24.
 */

public class MoreFragment extends Fragment {

    DrawerLayoutDemoActivity parentActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (DrawerLayoutDemoActivity) getActivity();
    }

    @OnClick(R.id.close_btn)
    void onCloseBtnClick() {
        parentActivity.closeDrawer();
    }


}
