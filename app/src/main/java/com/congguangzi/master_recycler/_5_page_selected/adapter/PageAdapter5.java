package com.congguangzi.master_recycler._5_page_selected.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.congguangzi.master_recycler.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 为代理 adapter 提供分页加载的功能.
 * <p>
 * 使用示例:
 * 1. 直接与数据交互的 adapter 需要继承 {@link IAppendData} 接口.
 * <pre>
 * public class NormalAdapter extends RecyclerView.Adapter<..> implements IAppendData<Item> {
 *      // ....
 *
 *     {@literal @}Override
 *      public void append(List<Item> set) {
 *          data.addAll(set);
 *          // 不需要调用 notify 方法, 外层的代理 adapter 会处理.
 *      }
 * }
 * </pre>
 * <p>
 * 2. client 端代码.
 * <pre>
 *      adapter = new NormalAdapter5();
 *      pageAdapter = new PageAdapter5(adapter);
 *      recyclerView.setAdapter(pageAdapter);
 *
 *      // 滑动监听.
 *      recyclerView.addOnScrollListener(new LoadMoreScrollListener5() {
 *         {@literal @}Override
 *          public void loadMore(int loadedItem, int pageSize) {
 *
 *              // 滑动到最底端时, 加载下一页数据.
 *              presenter.loadMore(loadedItem, pageSize);
 *          }
 *      });
 *
 *      // 加载第一页数据.
 *      presenter.loadMore(0, pageAdapter.pageSize());
 * </pre>
 * <p>
 * 3. 可与 {@link SelectedAdapter5} 共同代理一个类, 同时添加分页和选择的功能.
 * <pre>
 *     adapter = new NormalAdapter5();
 *     selectedAdapter = new SelectedAdapter5(adapter);
 *     pageAdapter = new PageAdapter5(selectedAdapter);
 *
 *     // pageAdapter 必须是最外层的代理, 因为涉及到与 RecyclerView 的滑动交互.
 *     recyclerView.setAdapter(pageAdapter);
 * </pre>
 * <p>
 *
 * @author congguangzi (congspark@163.com) 2018/3/29.
 */
public class PageAdapter5 extends BaseProxyAdapter {

    /**
     * 数据量的大小尽量可以展示满一个屏幕.
     */
    private final int PAGE_SIZE = 8;

    /**
     * 是否正在加载数据. true - 正在加载,
     */
    private boolean loading;

    /**
     * 所有数据是否加载完成. true - 全部加载完成, false - 仍有数据未加载.
     */
    private boolean loaded;

    private final int TYPE_ITEM = 0x01;
    private final int TYPE_BOTTOM = 0x02;

    public PageAdapter5(RecyclerView.Adapter adapter) {
        super(adapter);
        // 添加对 adapter 的判断.
        if (!(super.getAdapter() instanceof IAppendData)) {
            throw new RuntimeException("the " + adapter.getClass().getSimpleName()
                    + " should implement " + IAppendData.class.getSimpleName());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOTTOM) {
            return new PageViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout._2_load_more_bottom, parent, false));
        } else {
            return super.adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BOTTOM) {
            PageViewHolder viewHolder = (PageViewHolder) holder;
            if (loaded) {
                viewHolder.progressBar.setVisibility(View.GONE);
                viewHolder.tv_loaded.setVisibility(View.VISIBLE);
            } else {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.tv_loaded.setVisibility(View.GONE);
            }
        } else {
            super.adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == super.adapter.getItemCount()) {
            return TYPE_BOTTOM;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return super.adapter.getItemCount() + 1;
    }

    /**
     * 是否正在加载下一页数据
     *
     * @return true - 正在加载下一页数据.
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * 是否加载完成所有数据.
     *
     * @return true - 加载完成所有数据
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * {@link com.congguangzi.master_recycler._5_page_selected.listener.LoadMoreScrollListener5} 中,
     * 会修改加载状态. <br>
     * 此外, 如果需要记录当前加载状态的情况下(例如, 使用一个 RecyclerView 展示多个集合的数据, 动态切换 PageAdapter 时),
     * 也可以手动调用.
     *
     * @param loading true- 正在加载.
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    /**
     * @param loaded true- 已经加载完成
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * @return 每次加载条数.
     */
    public int pageSize() {
        return PAGE_SIZE;
    }

    /**
     * append next page data
     *
     * @param set data set load from the database or server
     */
    public void append(List set) {
        if (set.size() > 0) {
            // it can cast right, or an exception has thrown in the constructor
            ((IAppendData) super.getAdapter()).append(set);
            super.getProxyAdapter().notifyDataSetChanged();
        }
        if (set.size() < pageSize()) {
            loaded = true;
        }
        loading = false;
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress)
        ProgressBar progressBar;

        @BindView(R.id.tv_load)
        TextView tv_loaded;

        public PageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
