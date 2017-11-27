package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>
        implements LoadMore<Item> {

    // 是否正在分页加载.
    private boolean loading;

    // 是否全部加载完成.
    private boolean loaded;

    private List<Item> dataList = new ArrayList<>();

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder viewHolder = new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout._1_load_more_item, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item item = dataList.get(position);
        holder.title.setText(item.getTitle());
        holder.detail.setText(item.getDetail());
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void loadMore(List<Item> set) {
        dataList.addAll(set);
        notifyItemRangeInserted(dataList.size() - set.size(), set.size());
        if (set.size() < pageSize()) {
            loaded = true;
        }
        loading = false;
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public boolean loaded() {
        return loaded;
    }

    @Override
    public int pageSize() {
        return LoadMoreUtils.PAGE_SIZE;
    }

    @Override
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void setDataList(List<Item> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        TextView title;

        @BindView(R.id.item_detail)
        TextView detail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
