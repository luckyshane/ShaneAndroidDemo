package com.shane.me.shanedemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shane.me.shanedemo.R;

/**
 * Created by luckyshane on 2018/1/24.
 */

public class TextFragment extends Fragment {
    private static final String KEY_NAME = "name";
    private String name;


    public static TextFragment newFragment(String name) {
        TextFragment textFragment = new TextFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, name);
        textFragment.setArguments(bundle);
        return textFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getArguments().getString(KEY_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_text, container, false);
        TextView textView = (TextView) contentView.findViewById(R.id.center_tv);
        if (!TextUtils.isEmpty(name)) {
            textView.setText(name);
        }
        return contentView;
    }


}
