package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler._1_loadmore.Item;
import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreUtils;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分页加载 Recycler-View
 *
 * 带有 Loading 圈, 并且对 Adapter 做了优化. (通过 Proxy-adapter 的方式提取出分页的相关逻辑)
 *
 * 将对 RecyclerView 的时间监听移动到 RecyclerView 中.
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class OnlyRecyclerActivity extends BaseActivity implements LoadMoreView<Item> {

    @BindView(R.id.recycler)
    PageRecyclerView recycler;

    @Inject
    LoadMorePresenter presenter;

    private NormalAdapter_4 adapter;

    @Override
    protected void inject(RecyclerAppComponent appComponent) {
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
        presenter.loadMore(0, recycler.pageSize());
    }

    private void initRecyclerView() {
        adapter = new NormalAdapter_4();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getAppContext(), 2, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(gridLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.setAdapter(adapter, LoadMoreUtils.PAGE_SIZE);
        recycler.addOnScrollListener(new PageRecyclerView.LoadMoreScrollListener() {
            @Override
            public void loadMore(int page, int pageSize) {
                Toast.makeText(OnlyRecyclerActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
                presenter.loadMore(pageSize * page, pageSize);
            }
        });

    }

    @Override
    public void loadedMore(List<Item> set) {
        recycler.appendData(set);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
