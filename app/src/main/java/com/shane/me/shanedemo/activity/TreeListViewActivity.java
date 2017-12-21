package com.shane.me.shanedemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.adapter.ServerTreeListAdapter;
import com.shane.me.shanedemo.model.Server;
import com.shane.me.shanedemo.model.ServerNodeHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luckyshane on 2017/12/19.
 */
public class TreeListViewActivity extends BaseActivity {

    @BindView(R.id.tree_list_view)
    RecyclerView treeListView;
    ServerTreeListAdapter adapter;
    List<Server> serverList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_listview);
        ButterKnife.bind(this);
        initServerList();
        initView();
    }

    private void initView() {
        treeListView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ServerTreeListAdapter(serverList);

        treeListView.setAdapter(adapter);
    }


    private void initServerList() {
        serverList = new ArrayList<>();

        Server tmp;
        Server americas = Server.newServer("Americas");
        americas.setLevel(ServerNodeHelper.TYPE_CONTINENT);

        String[] countryList = new String[]{
                "Brazil",
                "Canada",
                "Mexico",
        };
        for (String countryStr : countryList) {
            tmp = Server.newCountryServer(countryStr);
            americas.addSubServer(tmp);
        }

        Server usa = Server.newCountryServer("United States");
        americas.addSubServer(usa);

        String[] usaStateList = new String[] {
                "US-Ashburn", "US-Atlanta",
                "US-Berkeley", "US-California",
                "US-Chicago", "US-Dallas",
        };
        for (String stateStr : usaStateList) {
            Server server = Server.newStateServer(stateStr);
            usa.addSubServer(server);
        }

        Server usaMiami = Server.newStateServer("US-Miami");
        usaMiami.addSubServer(Server.newCityServer("US-Miami1"));
        usaMiami.addSubServer(Server.newCityServer("US-Miami2"));
        usa.addSubServer(usaMiami);

        serverList.add(americas);

        Server asia = Server.newContinentServer("Asia Pacific");
        String[] asiaCountryList = new String[] {
                "Japan", "China",
        };
        for (String asiaCountry : asiaCountryList) {
            tmp = Server.newCountryServer(asiaCountry);
            asia.addSubServer(tmp);
        }
        serverList.add(asia);
    }


}
