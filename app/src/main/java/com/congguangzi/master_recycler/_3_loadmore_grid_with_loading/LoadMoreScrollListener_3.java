package com.congguangzi.master_recycler._3_loadmore_grid_with_loading;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.DefaultSpanSizeLookup;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.congguangzi.master_recycler._1_loadmore.PagingLoad_1;
import com.congguangzi.master_recycler._2_loadmore_with_loading.LoadingAdapterOptimize_2;

/**
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */

public abstract class LoadMoreScrollListener_3 extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    public LoadMoreScrollListener_3(RecyclerView recyclerView) {
        adapter = recyclerView.getAdapter();
        if (!(adapter instanceof PagingLoad_1)) {
            throw new RuntimeException("the adapter of the Page-RecyclerView should implement the PagingLoad_1 interface");
        }
        if (!(adapter instanceof LoadingAdapterOptimize_2)) {
            throw new RuntimeException("the adapter of the Page-RecyclerView should be the instance of LoadingAdapterOptimize_2");
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

        // 网格布局最后一行占整个布局.
        // 也是考虑到动态切换 LayoutManager .
        // 所以滑动时获取 spanSize.
//        if (layoutManager instanceof GridLayoutManager &&
//                ((GridLayoutManager) layoutManager).getSpanSizeLookup() instanceof DefaultSpanSizeLookup) {
//            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    if (position == adapter.getItemCount() - 1) {
//                        return ((GridLayoutManager) layoutManager).getSpanCount();
//                    } else {
//                        return 1;
//                    }
//                }
//            });
//        }

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int pageSize = ((PagingLoad_1) adapter).pageSize();
        if (adapter.getItemCount() >= pageSize
                && adapter.getItemCount() - 1 == lastVisibleItemPosition) {
            if (!((PagingLoad_1) adapter).isLoading() && !((PagingLoad_1) adapter).loaded()) {
                loadMore(adapter.getItemCount() / pageSize, pageSize);
            }
        }
    }

    /**
     * 加载更多.
     *
     * @param page  已经加载的页数.
     * @param count 每一页个数
     */
    public abstract void loadMore(int page, int count);

}
