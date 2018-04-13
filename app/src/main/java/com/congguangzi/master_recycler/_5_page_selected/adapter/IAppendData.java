package com.congguangzi.master_recycler._5_page_selected.adapter;

import java.util.List;

/**
 * 简介: 使用 {@link PagedAdapter5} 添加分页功能, 初始 adapter 需要继承的接口.
 *
 * @author congguangzi (congspark@163.com) 2018/3/30.
 */
public interface IAppendData<T> {

    /**
     * 向初始 adapter 的数据集合中追加 item
     * <p>
     * <b>NOTE:</b> 不需要在初始 adapter 中执行更新操作
     *
     * @param set 待添加的集合.
     */
    void appendData(List<T> set);


    /**
     * 重置初始 adapter 的数据集合
     * <p>
     * <b>NOTE:</b> 不需要在初始 adapter 中执行更新操作
     *
     * @param set 带替换的集合
     */
    void setData(List<T> set);

    /**
     * 清空 adapter 中的数据.
     * <p>
     * <b>NOTE:</b> 不需要在初始 adapter 中执行更新操作
     */
    void clearData();
}
