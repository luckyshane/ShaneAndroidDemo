package com.shane.me.shanedemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.model.MainItem;
import com.shane.me.shanedemo.util.ActionHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Asus on 2017/5/20.
 */

public class MainContentAdapter extends RecyclerView.Adapter {
    private List<MainItem> items = new ArrayList<>();
    private Context context;


    public MainContentAdapter(Context context) {
        this.context = context;
    }

    public MainContentAdapter(Context context, List<MainItem> itemList) {
        this.context = context;
        if (itemList != null) {
            items.addAll(itemList);
        }
    }

    public void updateDatas(List<MainItem> itemList) {
        items.clear();
        if (itemList != null) {
            items.addAll(itemList);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_main_content, parent, false);
        return new MainContentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MainContentViewHolder myHolder = (MainContentViewHolder) holder;
        MainItem item = items.get(position);
        myHolder.titleTv.setText(item.title);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(v, myHolder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    private void onItemClick (View view, int position) {
        MainItem item = items.get(position);
        Toast.makeText(context, item.title, Toast.LENGTH_SHORT).show();
        ActionHelper.doAction(context, item);
    }


    static class MainContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView titleTv;

        MainContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }








}
