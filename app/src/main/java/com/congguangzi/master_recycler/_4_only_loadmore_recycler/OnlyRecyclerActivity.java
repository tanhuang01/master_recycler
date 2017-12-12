package com.congguangzi.master_recycler._4_only_loadmore_recycler;

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
import com.congguangzi.master_recycler._1_loadmore.LoadMoreUtils_1;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView_1;
import com.congguangzi.master_recycler._2_loadmore_with_loading.LoadingAdapterOptimize_2;
import com.congguangzi.master_recycler._2_loadmore_with_loading.NormalAdapter_2;
import com.congguangzi.master_recycler._3_loadmore_grid_with_loading.LoadMoreGridActivity;
import com.congguangzi.master_recycler._3_loadmore_grid_with_loading.LoadMoreScrollListener_3;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlyRecyclerActivity extends BaseActivity implements LoadMoreView_1<Item_1> {

    @BindView(R.id.recycler)
    PageRecyclerView recycler;

    @Inject
    LoadMorePresenter_1 presenter;

    private NormalAdapter_4 adapter;

    @Override
    protected void inject(MasterApplicationComponent appComponent) {
        DaggerOnlyRecyclerComponent.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._4_activity_only_recycler);
        ButterKnife.bind(this);
        initRecyclerView();
        presenter.loadMore(LoadMoreUtils_1.PAGE_SIZE, 0);
    }

    private void initRecyclerView() {
        adapter = new NormalAdapter_4();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getAppContext(), 2, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(gridLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.setAdapter(adapter, LoadMoreUtils_1.PAGE_SIZE);
        recycler.addOnScrollListener(new PageRecyclerView.LoadMoreScrollListener() {

            @Override
            public void loadMore(int page, int pageSize) {
                Toast.makeText(OnlyRecyclerActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
                presenter.loadMore(pageSize, pageSize * page);
            }
        });

    }


    @Override
    public void loadedMore(List<Item_1> set) {
        recycler.appendData(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
