package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.content.Context;

import java.util.List;

/**
 * 分页加载 view, 滑动到最底端, 自动加载更多项.
 *
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */
public interface LoadMoreView<T> {
    /**
     * 加载更多后返回的数据.
     *
     * @param set 加载完成后更多数据.
     */
    void loadedMore(List<T> set);

    Context getContext();
}
