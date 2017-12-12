package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 分页加载 RecyclerView
 * <p>
 * 使用代理的方式为 adapter 提供分页加载的功能.
 * </p>
 *
 * @author congguangzi (congspark@163.com) 2017/12/12.
 */
public class PageRecyclerView extends RecyclerView {

    PageAdapter pageAdapter;

    public PageRecyclerView(Context context) {
        this(context, null);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    /**
     * 分页加载.
     * <p>
     * 如果每一页小于 5 项, 则不做分页处理.
     * </p>
     *
     * @param adapter
     * @param pageSize 每一页加载的大小. 最小为5项.
     */
    public void setAdapter(Adapter adapter, int pageSize) {
        if (!(adapter instanceof LoadMore)) {
            throw new RuntimeException("if you want a page loading, the adapter should implements the LoadMore interface");
        }
        if (pageSize < 5) {
            setAdapter(adapter);
        } else {
            pageAdapter = new PageAdapter(adapter, pageSize);
            setAdapter(pageAdapter);
        }
    }

    /**
     * @param set 分页加载的数据.
     */
    public void appendData(List set) {
        pageAdapter.loadMore(set);
    }


    /**
     * 简介: 分页加载 adapter.
     * <p>
     * 代理 adapter, 为原有的 adapter 增加分页的功能.
     * </p>
     */
    static class PageAdapter extends Adapter<ViewHolder> {

        // 数据显示 adapter.
        private Adapter adapter;

        private final int pageSize;

        private boolean loading;
        private boolean loaded;

        private final int TYPE_ITEM = 0x01;
        private final int TYPE_BOTTOM = 0x02;

        public PageAdapter(Adapter adapter, int pageSize) {
            this.adapter = adapter;
            this.pageSize = pageSize;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                return adapter.onCreateViewHolder(parent, viewType);
            } else {
                return new LoadingViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout._2_load_more_bottom, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (getItemViewType(position) == TYPE_BOTTOM) {
                if (loaded) {
                    LoadingViewHolder viewHolder = (LoadingViewHolder) holder;
                    viewHolder.progressBar.setVisibility(View.GONE);
                    viewHolder.tv_loaded.setVisibility(View.VISIBLE);
                }
            } else {
                adapter.onBindViewHolder(holder, position);
            }
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() < pageSize ? adapter.getItemCount() : adapter.getItemCount() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position > 0 && position == adapter.getItemCount()) {
                return TYPE_BOTTOM;
            } else {
                return TYPE_ITEM;
            }
        }

        public void loadMore(@NotNull List set) {
            if (set.size() > 0) {
                ((LoadMore) adapter).appendData(set);
                notifyItemRangeInserted(adapter.getItemCount() - set.size(), set.size());
            }
            if (set.size() < pageSize) {
                loaded = true;
                notifyItemChanged(adapter.getItemCount());
            }
            setLoading(false);
        }

        private boolean isLoading() {
            return loading;
        }

        void setLoading(boolean loading) {
            this.loading = loading;
        }

        boolean loaded() {
            return loaded;
        }

        int pageSize() {
            return pageSize;
        }


        class LoadingViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.progress)
            ProgressBar progressBar;

            @BindView(R.id.tv_load)
            TextView tv_loaded;

            public LoadingViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    /**
     * 简介: 分页加载的滑动监听
     * <p>
     * 因为 RecyclerView 中维护的是一个集合, 所以不需要通过代理来添加功能.
     * </p>
     */
    public static abstract class LoadMoreScrollListener extends OnScrollListener {

        LinearLayoutManager layoutManager;
        PageAdapter pageAdapter;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            // 因为项目中有动态切换 LayoutManager.
            // 所以在滑动时获取 LayoutManager
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            } else {
                throw new RuntimeException("the Layout Manager of the RecyclerView must be a LinearLayoutManager");
            }

            if (!(recyclerView.getAdapter() instanceof PageAdapter)) {
                throw new RuntimeException("the Page-recyclerView must have a PageAdapter");
            }
            pageAdapter = (PageAdapter) recyclerView.getAdapter();

            // 网格布局最后一行占整个布局.
            // 也是考虑到动态切换 LayoutManager .
            // 所以滑动时获取 spanSize.
            if (layoutManager instanceof GridLayoutManager &&
                    ((GridLayoutManager) layoutManager).getSpanSizeLookup() instanceof GridLayoutManager.DefaultSpanSizeLookup) {
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position == pageAdapter.getItemCount() - 1) {
                            return ((GridLayoutManager) layoutManager).getSpanCount();
                        } else {
                            return 1;
                        }
                    }
                });
            }

            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            int pageSize = pageAdapter.pageSize();
            if (pageAdapter.getItemCount() >= pageSize
                    && pageAdapter.getItemCount() - 1 == lastVisibleItemPosition) {
                if (!pageAdapter.isLoading() && !pageAdapter.loaded()) {
                    pageAdapter.setLoading(true);
                    loadMore(pageAdapter.getItemCount() / pageSize, pageSize);
                }
            }
        }

        /**
         * 加载更多数据
         * <p>
         * 滑动到最底端回调.
         * </p>
         *
         * @param page     已经加的页数.
         * @param pageSize 每一页大小.
         */
        public abstract void loadMore(int page, int pageSize);
    }


    /**
     * 分页加载 RecyclerView 的 Adapter 需要继承此接口.
     *
     * @param <T> 待加载的集合类型.
     */
    interface LoadMore<T> {
        void appendData(List<T> set);
    }
}
