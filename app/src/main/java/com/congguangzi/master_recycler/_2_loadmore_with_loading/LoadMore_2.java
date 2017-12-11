package com.congguangzi.master_recycler._2_loadmore_with_loading;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 简介: 分页加载 adapter 实现的接口.
 *
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */
public interface LoadMore_2<T> {

    /**
     * 加载更多数据. <br>
     *
     * <p>
     * <b>NOTE: </b> 仅仅需要向 adapter 的集合中添加数据, 并不需要执行 notify***() 方法.
     * </p>
     *
     * @param set 待添加的数据集合.
     */
    void append(@NotNull List<T> set);

}
