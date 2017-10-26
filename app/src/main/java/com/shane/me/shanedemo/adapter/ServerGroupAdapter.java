package com.shane.me.shanedemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.model.ServerGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luckyshane on 2017/10/25.
 */

public class ServerGroupAdapter extends BaseExpandableListAdapter {

    private static final String TAG = ServerGroupAdapter.class.getSimpleName();
    private Context context;
    private List<String> serverGroupNameList;
    private Map<String, ServerGroup> serverGroupMap;


    public ServerGroupAdapter(Context context) {
        this(context, null);
    }

    public ServerGroupAdapter(Context context, List<ServerGroup> serverGroupList) {
        this.context = context;
        serverGroupNameList = new ArrayList<>();
        serverGroupMap = new HashMap<>();

        if (serverGroupList != null) {
            for (ServerGroup serverGroup : serverGroupList) {
                if (serverGroup != null && !TextUtils.isEmpty(serverGroup.groupName)) {
                    serverGroupNameList.add(serverGroup.groupName);
                    serverGroupMap.put(serverGroup.groupName, serverGroup);
                }
            }
        }
    }

    public void update(List<ServerGroup> serverGroupList) {
        this.serverGroupMap.clear();
        this.serverGroupNameList.clear();
        if (serverGroupList != null) {
            for (ServerGroup serverGroup : serverGroupList) {
                if (serverGroup != null && !TextUtils.isEmpty(serverGroup.groupName)) {
                    serverGroupNameList.add(serverGroup.groupName);
                    serverGroupMap.put(serverGroup.groupName, serverGroup);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return serverGroupNameList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String groupName = serverGroupNameList.get(groupPosition);
        ServerGroup group = serverGroupMap.get(groupName);
        if (group != null) {
            return group.getServerCount();
        }
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String groupName = serverGroupNameList.get(groupPosition);
        return serverGroupMap.get(groupName);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String groupName = serverGroupNameList.get(groupPosition);
        ServerGroup group = serverGroupMap.get(groupName);
        if (group != null) {
            return group.getServer(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;        // TODO: 暂不清楚含义
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_server_group, null);
        }
        initGroupView(convertView, groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_server_item, null);
        }
        initServerView(convertView, groupPosition, childPosition);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initGroupView(View rootView, final int groupPosition) {
        TextView nameTv = (TextView) rootView.findViewById(R.id.group_name_tv);
        CheckBox groupCollectChb = (CheckBox) rootView.findViewById(R.id.group_collect_chb);

        final ServerGroup group = (ServerGroup) getGroup(groupPosition);

        nameTv.setText(group.groupName);
        groupCollectChb.setChecked(group.isCollected);

        groupCollectChb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                group.setTotalCollected(!group.isCollected);
                notifyDataSetChanged();
            }
        });

        /*groupCollectChb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d(TAG, "Group onCheckedChanged " + isChecked + ", group: " + groupPosition);

                //group.isCollected = isChecked;
                group.setTotalCollected(isChecked);
                notifyDataSetChanged();
            }
        });*/
    }

    private void initServerView(View rootView, final int groupPosition, final int childPosition) {
        TextView serverNameTv = (TextView) rootView.findViewById(R.id.server_name_tv);
        final CheckBox serverCollectChb = (CheckBox) rootView.findViewById(R.id.server_collect_chb);

        final ServerGroup group = (ServerGroup) getGroup(groupPosition);
        final ServerGroup.ServerItem serverItem = group.getServer(childPosition);

        serverNameTv.setText(serverItem.name);
        serverCollectChb.setChecked(serverItem.isCollected);

        serverCollectChb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverItem.isCollected = !serverItem.isCollected;
                List<ServerGroup.ServerItem> serverItemList = group.getServerItemList();
                boolean isAllCollected = true;
                for (ServerGroup.ServerItem item : serverItemList) {
                    if (!item.isCollected) {
                        isAllCollected = false;
                    }
                }
                group.isCollected = isAllCollected;

                notifyDataSetChanged();
            }
        });

/*        serverCollectChb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                Log.d(TAG, "Child onCheckedChanged " + isChecked + ", group: " + groupPosition + ", child: " + childPosition);

                serverItem.isCollected = isChecked;

                List<ServerGroup.ServerItem> serverItemList = group.getServerItemList();
                boolean isAllCollected = true;
                for (ServerGroup.ServerItem item : serverItemList) {
                    if (!item.isCollected) {
                        isAllCollected = false;
                    }
                }
                group.isCollected = isAllCollected;

                notifyDataSetChanged();
            }
        });*/
    }

}
