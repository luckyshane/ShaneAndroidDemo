package com.shane.me.shanedemo.model;

import java.util.List;

/**
 * Created by luckyshane on 2017/12/19.
 */

public class ServerNode {
    private TreeNode<Server> serverTreeNode;

    public ServerNode(Server data) {
        serverTreeNode = new TreeNode<>(data);

        List<Server> subServerList = data.getSubServerList();
        if (subServerList != null) {
            for (Server subServer : subServerList) {
                serverTreeNode.addChild(new TreeNode<>(subServer));
            }
        }
    }


    public Server getServer() {
        return serverTreeNode.getData();
    }

    public boolean isLeaf() {
        return serverTreeNode.isLeaf();
    }




}
