package com.shane.me.shanedemo.adapter;

import android.provider.ContactsContract;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shane.me.shanedemo.R;
import com.shane.me.shanedemo.model.ViewPagerDemoItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by Asus on 2017/5/21.
 */

public class ViewPagerDemoAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    List<ViewPagerDemoItem> itemList = new ArrayList<>();
    List<ImageView> indicatorViews = new ArrayList<>();
    LinearLayout indicatorPanel;

    private static final int PAGE_SIZE = 3;

    public ViewPagerDemoAdapter(LinearLayout indicatorPanel) {
        this.indicatorPanel = indicatorPanel;
    }

    public ViewPagerDemoAdapter(List<ViewPagerDemoItem> itemList, LinearLayout indicatorPanel) {
        this.indicatorPanel = indicatorPanel;
        if (itemList != null) {
            this.itemList.addAll(itemList);
        }
        initIndicators();
    }


    private void initIndicators() {
        indicatorViews.clear();
        indicatorPanel.removeAllViews();

        int pageCount = getCount();

        for (int i = 0; i < pageCount; i++) {
            ImageView imageView = new ImageView(indicatorPanel.getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;

            imageView.setLayoutParams(params);
            indicatorPanel.addView(imageView);

            indicatorViews.add(imageView);
        }
    }


    @Override
    public int getCount() {
        return (itemList.size() + PAGE_SIZE - 1) / PAGE_SIZE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View pageView = LayoutInflater.from(container.getContext()).inflate(R.layout.layout_item_view_pager, null);

        PageViewHolder holder = new PageViewHolder(pageView);
        holder.bindData(position, itemList);
        container.addView(pageView);
        return pageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void updateDatas(List<ViewPagerDemoItem> itemList) {
        this.itemList.clear();
        if (itemList != null) {
            this.itemList.addAll(itemList);
        }
        initIndicators();
        notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (indicatorViews != null) {
            for (int i = 0; i < indicatorViews.size(); i++) {
                if (i == position) {
                    indicatorViews.get(i).setImageResource(R.mipmap.page_indicator_focused);
                } else {
                    indicatorViews.get(i).setImageResource(R.mipmap.page_indicator_unfocused);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    final class PageViewHolder {

        @BindViews({R.id.image_one, R.id.image_two, R.id.image_three, R.id.image_four})
        ImageView [] images;

        PageViewHolder(View pageView) {
            ButterKnife.bind(this, pageView);
        }

        void bindData(int position, List<ViewPagerDemoItem> itemList) {
            int count = getCount();

            int start = position * PAGE_SIZE;
            int end = (position + 1) * PAGE_SIZE;
            int actualSize = itemList.size();

            if (position == count -1 && position > 0 && end > actualSize) {
                // last page and not only one page
                int delta = end - actualSize;
                start -= delta;
                end = actualSize;
            }
            end = Math.min(end, actualSize);
            for (int i = start, index = 0; i < end; i++, index++) {
                images[index].setImageResource(itemList.get(i).imageResource);
                images[index].setVisibility(View.VISIBLE);
                setListener(images[index], itemList.get(i));
            }
        }

        void setListener(ImageView view, final ViewPagerDemoItem item) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "click id: " + v.getId() + ", image: " + item.imageResource, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }





}
