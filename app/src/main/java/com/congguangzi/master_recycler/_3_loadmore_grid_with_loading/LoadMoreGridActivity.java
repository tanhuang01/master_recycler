package com.congguangzi.master_recycler._3_loadmore_grid_with_loading;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item;
import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreUtils;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView;
import com.congguangzi.master_recycler._2_loadmore_with_loading.LoadingAdapterOptimize_2;
import com.congguangzi.master_recycler._2_loadmore_with_loading.NormalAdapter_2;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 多列 Recycler-View 分页加载.
 * <p>
 * 带有 Loading 圈, 并且对 Adapter 做了优化. (通过 Proxy-adapter 的方式提取出分页的相关逻辑)
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class LoadMoreGridActivity extends BaseActivity implements LoadMoreView<Item> {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Inject
    LoadMorePresenter presenter;

    RecyclerView.Adapter adapter;
    LoadingAdapterOptimize_2 proxyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._3_activity_load_more_grid);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter.loadMore(LoadMoreUtils.PAGE_SIZE, 0);
    }

    @Override
    protected void inject(RecyclerAppComponent appComponent) {
        DaggerLoadMoreGridComponent.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        adapter = new NormalAdapter_2();
        proxyAdapter = new LoadingAdapterOptimize_2(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getAppContext(), 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == proxyAdapter.getItemCount() - 1) {
                    return 2; // the span count
                } else {
                    return 1;
                }
            }
        });
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(proxyAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnScrollListener(new LoadMoreScrollListener_3(recycler) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreGridActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
                proxyAdapter.setLoading(true);
                presenter.loadMore(count * page, count);
            }
        });
        presenter.loadMore(0, proxyAdapter.pageSize());
    }

    @Override
    public void loadedMore(List<Item> set) {
        proxyAdapter.loadMore(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
