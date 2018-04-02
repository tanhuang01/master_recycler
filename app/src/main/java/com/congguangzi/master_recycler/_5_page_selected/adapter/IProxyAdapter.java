package com.congguangzi.master_recycler._5_page_selected.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * 接口定义: 代理 adapter 需要继承的接口, 用于获取最内层的原始 adapter, 主要用于刷新数据. 以及最外层的与 {@link RecyclerView}
 * 交互的 adapter, 主要用于更新界面界面显示.
 * <p>
 * <b>NOTE: </b> 下一级指的是向原始 adapter 的层级方向; 上一级指的是向 RecyclerView 层级的方向.
 *
 * @author congguangzi (congspark@163.com) 2018/4/1.
 */
public interface IProxyAdapter {

    /**
     * @return 获取下一级的 adapter.
     */
    RecyclerView.Adapter getAdapter();

    /**
     * @return 获取上一级的 adapter.
     */
    RecyclerView.Adapter getProxyAdapter();

    /**
     * @return 设置上一级的 proxyAdapter.
     */
    void setProxyAdapter(RecyclerView.Adapter proxyAdapter);
}
