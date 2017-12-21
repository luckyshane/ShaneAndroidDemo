package com.shane.me.shanedemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luckyshane on 2017/12/19.
 */

public class TreeNode<T> {

    private TreeNode<T> parent;
    private List<TreeNode<T>> children;
    private boolean isExpanded;
    private T data;

    public TreeNode(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public void addChild(TreeNode<T> child) {
        if (child != null) {
            child.setParent(this);
            children.add(child);
        }
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children == null || children.isEmpty();
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public T getData() {
        return data;
    }
}
