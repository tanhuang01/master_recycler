package com.congguangzi.master_recycler._5_page_selected.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.congguangzi.master_recycler.R;

import java.util.List;

/**
 * 简介: adapter 的代理类, 为代理的 adapter 提供点击选中高亮的功能.
 * <p>
 * 使用示例:
 * <pre>
 *      adapter = new NormalAdapter5();
 *      selectedAdapter = new SelectedAdapter5(adapter); // adapter 非空, 否则抛出异常.
 *      selectedAdapter.setOnItemPositionSelectedListener(new SelectedAdapter5.OnItemPositionSelectedListener() {
 *
 *         {@literal @}Override
 *          public void onItemPosition(int position) {
 *               // 点击位置的回调
 *              Toast.makeText(getAppContext(), "pos " + position, Toast.LENGTH_SHORT).show();
 *          }
 *      });
 *      selectedAdapter.setOnItemObjectSelectedListener(new SelectedAdapter5.OnItemObjectSelectedListener<Item>() {
 *
 *         {@literal @}Override
 *          public void onItemObject(Item obj) {
 *              // 点击位置数据项的回调
 *              Toast.makeText(getAppContext(),  obj.toString() , Toast.LENGTH_SHORT).show();
 *          }
 *      });
 *
 *      // 使用代理的 adapter.
 *      recyclerView.setAdapter(selectedAdapter);
 * </pre>
 *
 * @param <T> adapter 每一个 item 关联的数据类型
 * @author congguangzi (congspark@163.com) 2018/3/30.
 */
public class SelectedAdapter5<T> extends BaseProxyAdapter {

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
     * @param adapter 被代理的 adapter.
     */
    public SelectedAdapter5(RecyclerView.Adapter adapter) {
        // 添加对 adapter 的判断.
        super(adapter);
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


    public SelectedAdapter5 setOnItemPositionSelectedListener(OnItemPositionSelectedListener l) {
        this.onItemPositionSelectedListener = l;
        return this;
    }


    public SelectedAdapter5 setOnItemObjectSelectedListener(OnItemObjectSelectedListener<T> l) {
        this.onItemObjectSelectedListener = l;
        return this;
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

            // 清除
            SelectedAdapter5.super.getProxyAdapter()
                    .notifyItemChanged(selectedIndex, KEY_UPDATE_CLEAR);

            // 点击项高亮显示
            selectedIndex = (int) v.getTag(R.id.key_position);
            SelectedAdapter5.super.getProxyAdapter()
                    .notifyItemChanged(selectedIndex, KEY_UPDATE_SELECTED);

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
