package com.congguangzi.master_recycler._2_loadmore_with_loading;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore_recycler.Item;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMore;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */

public class WithLoadingAdapter extends RecyclerView.Adapter
        implements LoadMore<Item> {

    private static final String TAG = "Loading";
    private boolean loading;
    private boolean loaded;

    private final int TYPE_ITEM = 0x01;
    private final int TYPE_BOTTOM = 0x02;

    private List<Item> data = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new WithLoadingViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout._1_load_more_item, parent, false));
        } else {
            return new WithLoadingViewHolderBottom(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout._2_load_more_bottom, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BOTTOM) {
            if (loaded) {
                WithLoadingViewHolderBottom viewHolderBottom = (WithLoadingViewHolderBottom) holder;
                viewHolderBottom.progressBar.setVisibility(View.GONE);
            }
        } else {
            Item item = data.get(position);
            WithLoadingViewHolder viewHolder = (WithLoadingViewHolder) holder;
            viewHolder.title.setText(item.getTitle());
            viewHolder.detail.setText(item.getDetail());
        }
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size() < pageSize() ? data.size() : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (data == null || data.size() > position) {
            return TYPE_ITEM;
        } else {
            return TYPE_BOTTOM;
        }
    }

    @Override
    public void loadMore(List<Item> set) {
        data.addAll(set);
        notifyDataSetChanged();
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

    static class WithLoadingViewHolderBottom extends RecyclerView.ViewHolder {

        @BindView(R.id.progress)
        ProgressBar progressBar;

        public WithLoadingViewHolderBottom(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
