package com.congguangzi.master_recycler._1_loadmore_recycler;

import java.util.List;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */
public interface LoadMore<T> {

    /**
     * 加载完成更多数据时.
     *
     * @param set
     */
    void loadMore(List<T> set);

    /**
     * 判断是否正在加载更多.
     *
     * @return true-加载中; false-其他情况.
     */
    boolean isLoading();

    /**
     * 设置当前的加载状态.
     *
     * @param loading
     */
    void setLoading(boolean loading);

    /**
     * 数据库中的数据是否以及加载完成. <br>
     *
     * @return true-加载完所有数据库中数据; false-数据库中仍有数据未加载.
     */
    boolean loaded();
}
