package com.shane.me.shanedemo.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.adapter.ServerGroupAdapter;
import com.shane.me.shanedemo.model.ServerGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by luckyshane on 2017/10/25.
 */

public class ServerFragment extends Fragment {
    private static final String TAG = ServerFragment.class.getSimpleName();
    private static final String KEY_TYPE = "type";

    public static final int TYPE_RECOMMEND = 0;
    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVOURITE = 2;

    private int type;
    private ServerGroupAdapter serverGroupAdapter;
    private Context context;
    private List<ServerGroup> serverGroupList;


    public static ServerFragment newInstance(int type) {
        if (type < TYPE_RECOMMEND || type > TYPE_FAVOURITE) {
            throw new IllegalArgumentException("type invalid");
        }
        ServerFragment fragment = new ServerFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private String getName() {
        switch (type) {
            case TYPE_RECOMMEND:
                return "recommend";
            case TYPE_ALL:
                return "all";
            case TYPE_FAVOURITE:
                return "favourite";
        }
        return type + "";
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach " + getName());
        attachContext(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach activity " + getName());
        attachContext(activity);
    }

    private void attachContext(Context context) {
        if (this.context == null) {
            this.context = context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        type = args.getInt(KEY_TYPE);
        Log.d(TAG, "onCreate " + getName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView " + getName());
        View rootView = inflater.inflate(R.layout.fragment_server, container, false);
        initData();
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated " + getName());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart " + getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume " + getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause " + getName());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop " + getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView " + getName());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy " + getName());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach " + getName());
    }

    private void initView(View rootView) {
        TextView textView = (TextView) rootView.findViewById(R.id.name_tv);
        textView.setText(type + "");

        ExpandableListView serverListView = (ExpandableListView) rootView.findViewById(R.id.server_list_view);

        serverGroupAdapter = new ServerGroupAdapter(context, serverGroupList);
        serverListView.setAdapter(serverGroupAdapter);
    }

    private void initData() {
        serverGroupList = new ArrayList<>();

        ServerGroup serverGroup = new ServerGroup("The Fastest Server");
        serverGroupList.add(serverGroup);

        serverGroup = new ServerGroup("HongKong");
        serverGroupList.add(serverGroup);

        serverGroup = new ServerGroup("United States");
        serverGroup.addServerItem(new ServerGroup.ServerItem("USA-Los Angeles"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("USA-New York"));
        serverGroupList.add(serverGroup);

        serverGroup = new ServerGroup("United Kingdom");
        serverGroup.addServerItem(new ServerGroup.ServerItem("England"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England1"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland1"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England2"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland3"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England3"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland3"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England4"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland4"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England5"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland5"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("England6"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Scotland6"));
        serverGroupList.add(serverGroup);

        serverGroup = new ServerGroup("China");
        serverGroup.addServerItem(new ServerGroup.ServerItem("Beijing"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Shanghai"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Chengdu"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Nanjing"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Shenzhen"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Guangzhou"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Nanchong"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Nanbu"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Urumuqi"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("YiLi"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Dalian"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Dongguan"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Haikou"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Suining"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Langzhong"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Dayi"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Taiwan"));
        serverGroup.addServerItem(new ServerGroup.ServerItem("Gaoxiong"));

        serverGroupList.add(serverGroup);
    }



}
