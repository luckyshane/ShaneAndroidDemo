package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.adapter.ViewPagerDemoAdapter;
import com.shane.me.shanedemo.model.ViewPagerDemoItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewPagerDemoActivity extends AppCompatActivity {

    @BindView(R.id.viewpager_test)
    ViewPager viewPager;

    @BindView(R.id.panel_indicators)
    LinearLayout indicatorsPanel;

    int [] images = new int[] {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d,
            R.mipmap.e, R.mipmap.f, R.mipmap.g, R.mipmap.h
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_demo);
        ButterKnife.bind(this);
        initViewPager();
    }

    private void initViewPager() {
        ViewPagerDemoAdapter adapter = new ViewPagerDemoAdapter(indicatorsPanel);
        viewPager.setAdapter(adapter);
        List<ViewPagerDemoItem> itemList = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ViewPagerDemoItem item = new ViewPagerDemoItem();
            item.imageResource = images[i];
            itemList.add(item);
        }
        adapter.updateDatas(itemList);
        viewPager.addOnPageChangeListener(adapter);
    }








}
