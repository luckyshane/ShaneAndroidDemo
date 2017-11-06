package com.shane.me.shanedemo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;

import com.shane.me.shanedemo.R;

/**
 * Created by luckyshane on 2017/11/6.
 */

public class SearchDemoActivity extends BaseActivity {

    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_demo);
        initView();
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(false); // 是否缩小为图标
        searchView.setSubmitButtonEnabled(false); // 显示搜索按钮


        SearchView.SearchAutoComplete tv = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        tv.setHintTextColor(Color.parseColor("#7A797B"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: "+ query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onQueryTextChange: "+ newText);
                return false;
            }
        });
    }




}
