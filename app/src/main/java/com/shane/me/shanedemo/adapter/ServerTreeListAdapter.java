package com.shane.me.shanedemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.model.Server;
import com.shane.me.shanedemo.model.ServerNodeHelper;
import com.shane.me.shanedemo.model.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luckyshane on 2017/12/19.
 */

public class ServerTreeListAdapter extends RecyclerView.Adapter {
    private List<TreeNode<Server>> allNodes = new ArrayList<>();
    private List<TreeNode<Server>> expandedNodes = new LinkedList<>();

    public ServerTreeListAdapter(List<Server> allNodes) {
        this.allNodes = ServerNodeHelper.newServerNodeList(allNodes);
        this.expandedNodes = ServerNodeHelper.getExpandedServerNodeList(this.allNodes);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;
        View itemView;
        RecyclerView.ViewHolder viewHolder;
        switch (viewType) {
            case ServerNodeHelper.TYPE_CONTINENT:
                layoutId = R.layout.list_item_continent;
                itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                viewHolder = new ContinentViewHolder(itemView);
                break;
            case ServerNodeHelper.TYPE_COUNTRY:
                layoutId = R.layout.list_item_country;
                itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                viewHolder = new CountryViewHolder(itemView);
                break;
            case ServerNodeHelper.TYPE_STATE:
                layoutId = R.layout.list_item_country;
                itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                viewHolder = new CountryViewHolder(itemView);
                break;
            default:
                layoutId = R.layout.list_item_country;
                itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
                viewHolder = new CountryViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TreeNode<Server> serverNode = expandedNodes.get(position);
        Server server = serverNode.getData();

        int viewType = getItemViewType(position);
        switch (viewType) {
            case ServerNodeHelper.TYPE_CONTINENT:
                ContinentViewHolder viewHolder = (ContinentViewHolder) holder;
                viewHolder.serverNameTv.setText(server.getShowName());
                if (serverNode.isLeaf()) {
                    viewHolder.collapseTv.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.collapseTv.setText(serverNode.isExpanded() ? "收缩" : "展开");
                    viewHolder.collapseTv.setVisibility(View.VISIBLE);
                }
                break;
            case ServerNodeHelper.TYPE_COUNTRY:
            default:
                CountryViewHolder countryViewHolder = (CountryViewHolder) holder;
                countryViewHolder.serverNameTv.setText(server.getShowName());
                if (serverNode.isLeaf()) {
                    countryViewHolder.collapseTv.setVisibility(View.INVISIBLE);
                } else {
                    countryViewHolder.collapseTv.setText(serverNode.isExpanded() ? "收缩" : "展开");
                    countryViewHolder.collapseTv.setVisibility(View.VISIBLE);
                }
                if (viewType != ServerNodeHelper.TYPE_COUNTRY) {
                    countryViewHolder.flagTv.setVisibility(View.INVISIBLE);
                } else {
                    countryViewHolder.flagTv.setVisibility(View.VISIBLE);
                }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (expandedNodes != null) {
            return expandedNodes.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        TreeNode<Server> node = expandedNodes.get(position);
        Server data = node.getData();
        return data.getLevel();
    }

    private void expandOrCollapse(int position) {
        TreeNode<Server> curNode = expandedNodes.get(position);
        if (curNode != null && !curNode.isLeaf()) {
            boolean isExpandedOld = curNode.isExpanded();
            if (isExpandedOld) {
                List<TreeNode<Server>> children = curNode.getChildren();
                for (TreeNode<Server> child : children) {
                    if (child.isExpanded()) {
                        collapse(child, position + 1);
                    }
                    expandedNodes.remove(position + 1);
                }
            } else {
                expandedNodes.addAll(position + 1, curNode.getChildren());
            }
            curNode.setExpanded(!isExpandedOld);
            notifyDataSetChanged();
        }
    }

    private void collapse(TreeNode<Server> serverNode,int position){
        serverNode.setExpanded(false);
        if (!serverNode.isLeaf()) {
            List<TreeNode<Server>> children = serverNode.getChildren();
            for (TreeNode<Server> child : children) {
                if (child.isExpanded()) {
                    collapse(child, position + 1);
                }
                expandedNodes.remove(position + 1);
            }
        }
    }


    private static final class ContinentViewHolder extends RecyclerView.ViewHolder {
        private TextView serverNameTv;
        private TextView collapseTv;

        public ContinentViewHolder(View itemView) {
            super(itemView);
            serverNameTv = (TextView) itemView.findViewById(R.id.server_name_tv);
            collapseTv = (TextView) itemView.findViewById(R.id.collapse_tv);
        }

    }

    private static final class CountryViewHolder extends RecyclerView.ViewHolder {
        private TextView serverNameTv;
        private TextView collapseTv;
        private TextView flagTv;

        public CountryViewHolder(View itemView) {
            super(itemView);
            serverNameTv = (TextView) itemView.findViewById(R.id.server_name_tv);
            collapseTv = (TextView) itemView.findViewById(R.id.collapse_tv);
            flagTv = (TextView) itemView.findViewById(R.id.flag_tv);
        }
    }





}
