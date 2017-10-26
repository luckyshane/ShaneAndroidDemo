package com.shane.me.shanedemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luckyshane on 2017/10/25.
 */

public final class ServerGroup {

    public String groupName;
    public boolean isCollected;


    private List<ServerItem> serverItemList = new ArrayList<>();

    public ServerGroup(String groupName) {
        this.groupName = groupName;
    }

    public void addServerItem(ServerItem serverItem) {
        serverItemList.add(serverItem);
    }

    public List<ServerItem> getServerItemList() {
        return serverItemList;
    }

    public static final class ServerItem {
        public String name;
        public boolean isCollected;

        public ServerItem() {

        }
        public ServerItem(String serverName) {
            this.name = serverName;
        }

    }

    public int getServerCount() {
        if (serverItemList != null) {
            return serverItemList.size();
        }
        return 0;
    }

    public ServerItem getServer(int index) {
        if (serverItemList != null) {
            if (index < serverItemList.size()) {
                return serverItemList.get(index);
            }
        }
        return null;
    }

    public void setTotalCollected(boolean isCollected) {
        this.isCollected = isCollected;
        if (serverItemList != null) {
            for (ServerItem item : serverItemList) {
                item.isCollected = isCollected;
            }
        }
    }


}
