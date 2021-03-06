package com.congguangzi.master_recycler._5_page_selected.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.congguangzi.master_recycler._5_page_selected.adapter.PagedAdapter5;

/**
 * 简介: Recycler 的滑动监听. 当最后一个 item 出现在屏幕上的时候, 回调加载下一页的方法.
 * <p>
 * <b>NOTE:</b> 回调的参数为, [已经加载的条数] 和 [下一页要加载的条数].
 *
 * @author congguangzi (congspark@163.com) 2018/3/30.
 */

public abstract class LoadMoreScrollListener5 extends RecyclerView.OnScrollListener {

    LinearLayoutManager layoutManager;

    RecyclerView.Adapter adapter;

    public LoadMoreScrollListener5() {
        // get the adapter in the onScroll method
    }

    public LoadMoreScrollListener5(RecyclerView.Adapter adapter) {
        judgeAdapter(adapter);
        this.adapter = adapter;
    }

    public LoadMoreScrollListener5(RecyclerView recyclerView) {
        this(recyclerView.getAdapter());
    }

    /**
     * adapter must be  {@link PagedAdapter5}, or an Exception will be thrown.
     */
    private void judgeAdapter(RecyclerView.Adapter adapter) {

//        // 判断是否为 代理 adapter
//        if (!(adapter instanceof BaseProxyAdapter)) {
//            throw new RuntimeException("the " + getClass().getSimpleName() + "must initalized with a "
//                    + BaseProxyAdapter.class.getSimpleName());
//        }
//
//        // 获取 PagedAdapter5,
//        while (adapter != null) {
//            if (adapter instanceof PagedAdapter5) {
//                this.adapter = adapter;
//                break;
//            } else {
//                adapter = ((BaseProxyAdapter) adapter).getDataAdapter();
//            }
//        }
//
//        // 代理链中必须有一个是 PageAdapter
//        if (this.adapter == null) {
//            throw new RuntimeException("the " + getClass().getSimpleName() + " must initialized with a "
//                    + PagedAdapter5.class.getSimpleName());
//        }

        // 目前认为, PageAdapter 为最外层代理.
        if (!(adapter instanceof PagedAdapter5)) {
            throw new RuntimeException("the " + getClass().getSimpleName() + " must initialized with a "
                    + PagedAdapter5.class.getSimpleName());
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        // initial the adapter if necessary
        if (adapter == null) {
            adapter = recyclerView.getAdapter();

            judgeAdapter(adapter);
        }

        // initial the LayoutManager
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        } else {
            throw new RuntimeException("the Layout Manager of the RecyclerView must be a LinearLayoutManager");
        }

        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
        int pageSize = ((PagedAdapter5) adapter).pageSize();

        // when the last item, the loading item with progress bar, is showing,
        // start to get the data to show the next page.
        if (adapter.getItemCount() >= pageSize
                && adapter.getItemCount() - 1 == lastVisibleItemPosition) {
            if (!((PagedAdapter5) adapter).isLoading() && !((PagedAdapter5) adapter).isLoaded()) {
                ((PagedAdapter5) adapter).setLoading(true);
                loadMore(adapter.getItemCount() - 1, pageSize);
            }
        }
    }

    /**
     * load another page data.
     *
     * @param loadedItem items have loaded form the server / database
     * @param pageSize   one page size
     */
    public abstract void loadMore(int loadedItem, int pageSize);
}
