package com.shane.me.shanedemo.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luckyshane on 2017/12/19.
 */

public class ServerNodeHelper {
    public static final int TYPE_CONTINENT = 0;
    public static final int TYPE_COUNTRY = 1;
    public static final int TYPE_STATE = 2;
    public static final int TYPE_CITY = 3;

    public static List<TreeNode<Server>> newServerNodeList(List<Server> serverList) {
        if (serverList != null) {
            List<TreeNode<Server>> serverNodeList = new ArrayList<>();
            for (Server server : serverList) {
                serverNodeList.add(newServerNode(server));
            }
            return serverNodeList;
        }
        return null;
    }

    public static List<TreeNode<Server>> getExpandedServerNodeList(List<TreeNode<Server>> allServerNodeList) {
        List<TreeNode<Server>> linkedServerList = new LinkedList<>();
        linkedServerList.addAll(allServerNodeList);
        return linkedServerList;
    }


    public static TreeNode<Server> newServerNode(Server server) {
        TreeNode<Server> serverTreeNode = new TreeNode<>(server);
        List<Server> subServerList = server.getSubServerList();
        if (subServerList != null) {
            for (Server subServer : subServerList) {
                TreeNode<Server> subServerNode = newServerNode(subServer);
                serverTreeNode.addChild(subServerNode);
            }
        }
        return serverTreeNode;
    }




}
