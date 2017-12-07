package com.congguangzi.master_recycler._2_loadmore_with_loading;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore_recycler.Item;
import com.congguangzi.master_recycler._1_loadmore_recycler.ItemAdapter;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMore;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMorePresenter;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreRecyclerActivity;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreScrollListener;
import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreView;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreWithLoadingActivity extends BaseActivity implements LoadMoreView<Item> {

    @Inject
    LoadMorePresenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    RecyclerView.Adapter adapter;

    @Override
    protected void inject(MasterApplicationComponent appComponent) {
        DaggerLoadMoreWithLoadingComponent.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_load_more_recycler_layout);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter.loadMore(((LoadMore) adapter).pageSize(), 0);
    }

    private void initRecyclerView() {
        adapter = new WithLoadingAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnScrollListener(new LoadMoreScrollListener(recycler) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreWithLoadingActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
                ((LoadMore) adapter).setLoading(true);
                presenter.loadMore(count, count * page);
            }
        });
    }

    @Override
    public void loadedMore(List<Item> set) {
        ((LoadMore) adapter).loadMore(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
