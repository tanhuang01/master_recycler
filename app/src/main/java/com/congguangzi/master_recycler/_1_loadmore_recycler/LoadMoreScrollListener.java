package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public LoadMoreScrollListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        adapter = recyclerView.getAdapter();
        if (!(adapter instanceof PagingLoad)) {
            throw new RuntimeException("the adapter of the RecyclerView should implement the PagingLoad interface");
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // 因为项目中有动态切换 LayoutManager.
        // 所以在滑动时获取 LayoutManager
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        } else {
            throw new RuntimeException("the Layout Manager of the RecyclerView must be a LinearLayoutManager");
        }

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int pageSize = ((PagingLoad) adapter).pageSize();
        if (adapter.getItemCount() >= pageSize
                && adapter.getItemCount() - 1 == lastVisibleItemPosition) {
            if (!((PagingLoad) adapter).isLoading() && !((PagingLoad) adapter).loaded()) {
                loadMore(adapter.getItemCount() / pageSize, pageSize);
            }
        }
    }

    /**
     * 加载更多.
     *
     * @param page  已经加载的页数.
     * @param count 每一页个数, 默认值为 20
     */
    public abstract void loadMore(int page, int count);

}
