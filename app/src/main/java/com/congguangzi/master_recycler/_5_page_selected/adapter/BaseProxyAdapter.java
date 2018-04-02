package com.congguangzi.master_recycler._5_page_selected.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * 简介: 代理 adapter 需要继承的基类, 用于获取最内层的 adapter, 主要用于刷新数据.
 * 以及最外层的与 {@link RecyclerView} 交互的 adapter, 主要用于更新界面界面显示.
 * <p>
 * 具体使用方式, 参见 {@link PageAdapter5} 和 {@link SelectedAdapter5}
 *
 * @author congguangzi (congspark@163.com) 2018/4/2.
 */
public abstract class BaseProxyAdapter extends RecyclerView.Adapter {

    /**
     * 内层的 adapter
     */
    protected RecyclerView.Adapter adapter;

    /**
     * 外层的 adapter
     * <p>
     * <b>NOTE:</b> 因为通过代理的方式添加功能, 所以可能存在多层代理.
     */
    private RecyclerView.Adapter proxyAdapter;


    BaseProxyAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        if (adapter == null) {
            throw new RuntimeException("the adapter to be proxy can NOT be NULL");
        }

        // 如果是一个 代理adapter, 那么需要设置上级.
        if (adapter instanceof BaseProxyAdapter) {
            ((BaseProxyAdapter) adapter).setProxyAdapter(this);
        }
    }

    /**
     * @return 最内层的 adapter. 即与数据交互的 adapter.
     */
    public RecyclerView.Adapter getAdapter() {
        // 被代理的 adapter 一定非空
        return adapter instanceof BaseProxyAdapter ? ((BaseProxyAdapter) adapter).getAdapter() : adapter;
    }

    /**
     * @return 最外层代理的 adapter. 即与 RecyclerView 直接交互的 adapter.
     */
    protected RecyclerView.Adapter getProxyAdapter() {
        if (proxyAdapter == null) {
            return this;
        }
        return proxyAdapter instanceof BaseProxyAdapter ? ((BaseProxyAdapter) proxyAdapter).getProxyAdapter() : proxyAdapter;
    }

    private void setProxyAdapter(RecyclerView.Adapter proxyAdapter) {
        this.proxyAdapter = proxyAdapter;
    }
}
