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
import com.congguangzi.master_recycler._1_loadmore.Item_1;
import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter_1;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreScrollListener_1;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreUtils_1;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView_1;
import com.congguangzi.master_recycler._2_loadmore_with_loading.LoadingAdapterOptimize_2;
import com.congguangzi.master_recycler._2_loadmore_with_loading.NormalAdapter_2;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreGridActivity extends BaseActivity implements LoadMoreView_1<Item_1> {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @Inject
    LoadMorePresenter_1 presenter;

    RecyclerView.Adapter adapter;
    LoadingAdapterOptimize_2 proxyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_grid);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter.loadMore(LoadMoreUtils_1.PAGE_SIZE, 0);
    }

    @Override
    protected void inject(MasterApplicationComponent appComponent) {
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
        recycler.setLayoutManager(gridLayoutManager);
        recycler.setAdapter(proxyAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnScrollListener(new LoadMoreScrollListener_1(recycler) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreGridActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
                proxyAdapter.setLoading(true);
                presenter.loadMore(count, count * page);
            }
        });
    }

    @Override
    public void loadedMore(List<Item_1> set) {
        proxyAdapter.loadMore(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
