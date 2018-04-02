package com.congguangzi.master_recycler._5_page_selected;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item;
import com.congguangzi.master_recycler._5_page_selected.adapter.IAppendData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 基础的 adapter 仅仅提供数据的展示, 为简化逻辑仅仅做文字的显示, 不涉及图片的现实操作.
 *
 * @author congguangzi (congspark@163.com) 2018/3/29.
 */
public class NormalAdapter5 extends RecyclerView.Adapter<NormalAdapter5.NormalViewHolder> implements IAppendData<Item> {

    private List<Item> data = new ArrayList<>();

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout._1_load_more_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, int position) {
        Item item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.detail.setText(item.getDetail());

        //
        holder.itemView.setTag(R.id.key_object, item);
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * 重置当前 adapter 中的集合, 并且刷新.
     *
     * @param set 带替换的集合.
     */
    public void setData(@Nullable List<Item> set) {
        if (set == null || set.isEmpty()) {
            return;
        }
        data = set;
        notifyDataSetChanged();
    }

    @Override
    public void append(List<Item> set) {
        data.addAll(set);
    }


    static class NormalViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_detail)
        TextView detail;

        NormalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
