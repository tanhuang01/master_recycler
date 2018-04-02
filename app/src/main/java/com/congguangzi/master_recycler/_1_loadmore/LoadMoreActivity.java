package com.congguangzi.master_recycler._1_loadmore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 滑动到底端加载更多 recyclerView
 * <p>
 * 最基本的 Recycler-View 分页加载效果, 每当滑动到最底端时, 去加载下一页的数据,
 * 下一页的数据直接添加到 adapter 的数据集合中, 然后更新
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class LoadMoreActivity extends BaseActivity implements LoadMoreView<Item> {

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
        presenter.loadMore(((PagingLoad_1) adapter).pageSize(), 0);
    }

    @Override
    protected void inject(RecyclerAppComponent appComponent) {
        DaggerLoadMoreComponent.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    private void initRecyclerView() {
        adapter = new ItemAdapter_1();
        recycleView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new LoadMoreScrollListener_1(recycleView) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreActivity.this.getAppContext(), "load " + (page + 1), Toast.LENGTH_SHORT).show();
                ((PagingLoad_1) adapter).setLoading(true);
                presenter.loadMore(count * page, count);
            }
        });
        presenter.loadMore(0, 7);
    }


    @Override
    public void loadedMore(List<Item> set) {
        ((PagingLoad_1<Item>) adapter).loadMore(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
