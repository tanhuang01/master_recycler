package com.congguangzi.master_recycler._5_page_selected.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.congguangzi.master_recycler.R;

import java.util.List;

/**
 * 简介: adapter 的代理类, 提供点击选中的功能.
 *
 * @param <T>
 * @author congguangzi (congspark@163.com) 2018/3/30.
 */
public class SelectedAdapter5<T> extends RecyclerView.Adapter implements IProxyAdapter {

    // 更新背景色 payload 标记
    private final int KEY_UPDATE_SELECTED = 0x01;

    // 清除背景色 paylaod 标记
    private final int KEY_UPDATE_CLEAR = 0x02;

    /**
     * 选中 item 的下标
     */
    private int selectedIndex = -1;

    /**
     * 点击位置的回调
     */
    private OnItemPositionSelectedListener onItemPositionSelectedListener;

    /**
     * 点击位置对应的数据对象回调
     */
    private OnItemObjectSelectedListener<T> onItemObjectSelectedListener;

    /**
     * {@link SelectedAdapter5} 代理的 adapter. 给该 adapter 添加点击 item 高亮显示和点击位置和数据的回调
     */
    RecyclerView.Adapter adapter;

    /**
     * 代理 {@link SelectedAdapter5} 的 adapter. 上一级代理的引用.
     * <p>
     * <b>NOTE:</b> 因为通过代理的方式添加功能, 所以可能存在多层代理.
     */
    RecyclerView.Adapter proxyAdapter;

    /**
     * @see SelectedAdapter5#SelectedAdapter5(RecyclerView.Adapter, OnItemPositionSelectedListener, OnItemObjectSelectedListener)
     */
    public SelectedAdapter5(RecyclerView.Adapter adapter) {
        // 添加对 adapter 的判断.
        if (adapter == null) {
            throw new RuntimeException("the adapter to be proxy can NOT be NULL");
        }
        this.adapter = adapter;

        // 如果 adapter 是一个代理, 那么需要设置上级.
        if (adapter instanceof IProxyAdapter) {
            ((IProxyAdapter) adapter).setProxyAdapter(this);
        }
    }

    /**
     * @see SelectedAdapter5#SelectedAdapter5(RecyclerView.Adapter, OnItemPositionSelectedListener, OnItemObjectSelectedListener)
     */
    public SelectedAdapter5(RecyclerView.Adapter adapter, OnItemPositionSelectedListener l) {
        this(adapter);
        this.onItemPositionSelectedListener = l;
    }

    /**
     * @see SelectedAdapter5#SelectedAdapter5(RecyclerView.Adapter, OnItemPositionSelectedListener, OnItemObjectSelectedListener)
     */
    public SelectedAdapter5(RecyclerView.Adapter adapter, OnItemObjectSelectedListener<T> l) {
        this(adapter);
        this.onItemObjectSelectedListener = l;
    }

    /**
     * @param adapter          被代理的 adapter
     * @param positionListener 被点击 item 的位置监听
     * @param itemListener     被点击 item 的数据监听
     */
    public SelectedAdapter5(RecyclerView.Adapter adapter, OnItemPositionSelectedListener positionListener, OnItemObjectSelectedListener<T> itemListener) {
        this(adapter);
        this.onItemPositionSelectedListener = positionListener;
        this.onItemObjectSelectedListener = itemListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        adapter.onBindViewHolder(holder, position);

        // 防止每次绑定时, 都创建新的 OnItemSelectedListener.
        // 确保 OnItemSelectedListener 的数量与 ViewHolder 的数量相同
        OnItemSelectedListener selectedListener = (OnItemSelectedListener) holder.itemView.getTag(R.id.key_listener);
        if (selectedListener == null) {
            selectedListener = new OnItemSelectedListener();
            holder.itemView.setTag(R.id.key_listener, selectedListener);
//            Log.i("activity_5_sel", "add selctedListener" + position);
        }

        // 设置背景色的选择状态.
        holder.itemView.setSelected(selectedIndex == position);

        holder.itemView.setTag(R.id.key_position, position);
//        Log.e("activity_5_sel", "add position: " + position);
        holder.itemView.setOnClickListener(selectedListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (payloads == null || payloads.isEmpty()) {
            //
            onBindViewHolder(holder, position);
        } else {
            // 更新选择状态
            switch (((int) payloads.get(0))) {
                case KEY_UPDATE_SELECTED:
                    holder.itemView.setSelected(true);
                    break;
                case KEY_UPDATE_CLEAR:
                    holder.itemView.setSelected(false);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return adapter == null ? 0 : adapter.getItemCount();
    }


    public void setOnItemPositionSelectedListener(OnItemPositionSelectedListener l) {
        this.onItemPositionSelectedListener = l;
    }

    public void setOnItemObjectSelectedListener(OnItemObjectSelectedListener<T> l) {
        this.onItemObjectSelectedListener = l;
    }

    /**
     * @return 下一级adapter, 该 {@link SelectedAdapter5} 代理的 adapter
     */
    @Override
    public RecyclerView.Adapter getAdapter() {
        // 被代理的 adapter 一定非空.
        return adapter instanceof IProxyAdapter ? ((IProxyAdapter) adapter).getAdapter() : adapter;
    }

    /**
     * @return 上一级 adapter, 代理 {@link SelectedAdapter5} 的 adapter
     */
    @Override
    public RecyclerView.Adapter getProxyAdapter() {
        if (proxyAdapter == null) {
            return this;
        }
        return proxyAdapter instanceof IProxyAdapter ? ((IProxyAdapter) proxyAdapter).getProxyAdapter() : proxyAdapter;
    }

    @Override
    public void setProxyAdapter(@NonNull RecyclerView.Adapter proxyAdapter) {
        this.proxyAdapter = proxyAdapter;
    }

    /**
     * 简介: RecyclerView 列表中, 每一个 item 的监听.
     */
    public class OnItemSelectedListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            // 防止重复点击
            if (v.isSelected()) {
                return;
            }

            getProxyAdapter().notifyItemChanged(selectedIndex, KEY_UPDATE_CLEAR);
            selectedIndex = (int) v.getTag(R.id.key_position);
            getProxyAdapter().notifyItemChanged(selectedIndex, KEY_UPDATE_SELECTED);

            if (onItemPositionSelectedListener != null) {
                onItemPositionSelectedListener.onItemPosition((Integer) v.getTag(R.id.key_position));
            }

            if (onItemObjectSelectedListener != null) {
                onItemObjectSelectedListener.onItemObject((T) v.getTag(R.id.key_object));
            }
        }
    }

    /**
     * 接口定义: 点击 recycler 某一项时的回调
     */
    public interface OnItemPositionSelectedListener {

        /**
         * 点击每一个item时, 回调点击的 item, 在 recycler 中的位置.
         *
         * @param position 被点击的 item 在recycler 中的位置. 与 adapter 相同.
         */
        void onItemPosition(int position);
    }

    /**
     * 接口定义: 点击 recycler 某一项时的回调
     */
    public interface OnItemObjectSelectedListener<T> {

        /**
         * 点击每一个 item 时, 回调被点击 item 对应的数据对象
         *
         * @param obj 被点击 item 对应的数据对象.
         */
        void onItemObject(T obj);
    }

}
