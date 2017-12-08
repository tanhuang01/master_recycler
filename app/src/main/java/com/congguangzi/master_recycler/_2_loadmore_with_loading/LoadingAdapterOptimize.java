package com.congguangzi.master_recycler._2_loadmore_with_loading;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore_recycler.Item;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreUtils;
import com.congguangzi.master_recycler._1_loadmore_recycler.PagingLoad;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */
public class LoadingAdapterOptimize extends RecyclerView.Adapter implements PagingLoad<Item> {

    private static final String TAG = "Loading";
    private boolean loading;
    private boolean loaded;

    private final int TYPE_ITEM = 0x01;
    private final int TYPE_BOTTOM = 0x02;

    private RecyclerView.Adapter adapter;

    public LoadingAdapterOptimize(RecyclerView.Adapter adapter) {
        if (adapter == null || !(adapter instanceof LoadMore)) {
            throw new RuntimeException("the parameter adapter should not be NULL and must implement LoadMore<T>");
        }
        this.adapter = adapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return adapter.onCreateViewHolder(parent, viewType);
        } else {
            return new LoadingViewHolderBottom(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout._2_load_more_bottom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BOTTOM) {
            if (loaded) {
                LoadingViewHolderBottom viewHolder = (LoadingViewHolderBottom) holder;
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.tv_loaded.setVisibility(View.VISIBLE);
            }
        } else {
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() < pageSize() ? adapter.getItemCount() : adapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position > 0 && position == adapter.getItemCount()) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void loadMore(@NotNull List<Item> set) {
        if (set.size() > 0) {
            ((LoadMore) adapter).append(set);
            notifyItemRangeInserted(adapter.getItemCount() - set.size(), set.size());
        }
        if (set.size() < pageSize()) {
            loaded = true;
            notifyItemChanged(adapter.getItemCount());
        }
        loading = false;
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public boolean loaded() {
        return loaded;
    }

    @Override
    public int pageSize() {
        return LoadMoreUtils.PAGE_SIZE;
    }

    static class LoadingViewHolderBottom extends RecyclerView.ViewHolder {

        @BindView(R.id.progress)
        ProgressBar progressBar;

        @BindView(R.id.tv_load)
        TextView tv_loaded;

        public LoadingViewHolderBottom(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
