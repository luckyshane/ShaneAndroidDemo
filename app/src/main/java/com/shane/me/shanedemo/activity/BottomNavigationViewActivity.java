package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.fragment.TextFragment;

/**
 * Created by luckyshane on 2018/1/24.
 */

public class BottomNavigationViewActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);

        final FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        //bottomNavigationView.setItemTextColor();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.fav_menu:
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.main_container, TextFragment.newFragment("Favourite"));
                        transaction.commit();
                        break;
                    case R.id.schedules_menu:
                        fragmentManager.beginTransaction().replace(R.id.main_container, TextFragment.newFragment("Schedule")).commit();
                        break;
                    case R.id.music_menu:
                        fragmentManager.beginTransaction().replace(R.id.main_container, TextFragment.newFragment("Music")).commit();
                        break;
                }
                return true;
            }
        });
        //bottomNavigationView.setSelectedItemId(R.id.fav_menu);
    }


}
