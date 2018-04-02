package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.congguangzi.master_recycler._4_only_loadmore_recycler.NormalAdapter_4.WithLoadingViewHolder;

/**
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */

public class NormalAdapter_4 extends RecyclerView.Adapter<WithLoadingViewHolder> implements PageRecyclerView.LoadMore<Item> {

    private List<Item> data = new ArrayList<>();

    @Override
    public WithLoadingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WithLoadingViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout._1_load_more_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WithLoadingViewHolder holder, int position) {
        Item item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.detail.setText(item.getDetail());
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public void appendData(List<Item> set) {
        data.addAll(set);
    }

    static class WithLoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_detail)
        TextView detail;

        WithLoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
