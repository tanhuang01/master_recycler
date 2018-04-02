package com.congguangzi.master_recycler._5_page_selected;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item;
import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView;
import com.congguangzi.master_recycler._5_page_selected.adapter.PageAdapter5;
import com.congguangzi.master_recycler._5_page_selected.adapter.SelectedAdapter5;
import com.congguangzi.master_recycler._5_page_selected.listener.LoadMoreScrollListener5;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 简介: 分页选中加载 Recycler
 * <p>
 * 提供分页加载的功能; 同时也提供点击选中的功能;
 * <p>
 * 通过叠加不同的 adapter 实现. 实现动态的添加功能.
 * <p>
 *
 * @author congguangzi
 */
public class Activity5 extends BaseActivity implements LoadMoreView<Item> {

    @BindView(R.id.recycler_5)
    RecyclerView recyclerView;

    @BindView(R.id.pr_loading)
    ProgressBar pr_loading;

    NormalAdapter5 adapter;

    PageAdapter5 pageAdapter;

    SelectedAdapter5<Item> selectedAdapter;

    @Inject
    LoadMorePresenter presenter;


    @Override
    protected void inject(RecyclerAppComponent appComponent) {
        DaggerActivity5Component.builder()
                .masterComponent(appComponent)
                .bindActivity(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_5);
        ButterKnife.bind(this);


        adapter = new NormalAdapter5();
        selectedAdapter = new SelectedAdapter5(adapter);
        pageAdapter = new PageAdapter5(selectedAdapter);

        selectedAdapter.setOnItemPositionSelectedListener(new SelectedAdapter5.OnItemPositionSelectedListener() {
            @Override
            public void onItemPosition(int position) {

            }
        });
        selectedAdapter.setOnItemObjectSelectedListener(new SelectedAdapter5.OnItemObjectSelectedListener<Item>() {
            @Override
            public void onItemObject(Item obj) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(pageAdapter);
        recyclerView.addOnScrollListener(new LoadMoreScrollListener5() {
            @Override
            public void loadMore(int loadedItem, int pageSize) {
                Log.i("activity5_page", "start loading...");
                presenter.loadMore(loadedItem, pageSize);
            }
        });

        presenter.loadMore(0, pageAdapter.pageSize());
    }

    @Override
    public void loadedMore(List<Item> set) {
        // 更多数据加载完成
        Log.i("activity5_page", "loaded " + set.size());
        if (pr_loading.getVisibility() == View.VISIBLE) {
            pr_loading.setVisibility(View.GONE);
        }
        pageAdapter.append(set);
    }

    @Override
    public Context getAppContext() {
        return this;
    }
}
