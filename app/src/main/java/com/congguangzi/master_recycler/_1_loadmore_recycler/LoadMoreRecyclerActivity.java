package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 滑动到底端加载更多 recyclerView
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class LoadMoreRecyclerActivity extends BaseActivity implements LoadMoreView<Item> {

    @Inject
    LoadMorePresenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycleView;

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_load_more_recycler_layout);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter.loadMore(((LoadMore) adapter).pageSize(), 0);
    }

    @Override
    protected void inject(MasterApplicationComponent appComponent) {
        DaggerLoadMoreRecyclerComponent.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        adapter = new ItemAdapter();
        recycleView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new LoadMoreScrollListener(recycleView) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreRecyclerActivity.this.getAppContext(), "load " + (page + 1), Toast.LENGTH_SHORT).show();
                ((LoadMore) adapter).setLoading(true);
                presenter.loadMore(count, count * page);
            }
        });
    }


    @Override
    public void loadedMore(List<Item> set) {
        ((LoadMore<Item>) adapter).loadMore(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}