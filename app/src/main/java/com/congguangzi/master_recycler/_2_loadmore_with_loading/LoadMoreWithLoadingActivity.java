package com.congguangzi.master_recycler._2_loadmore_with_loading;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
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
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 分页加载 Recycler-View.
 *
 * 添加了一个 Loading 圈, 分页加载时显示. 并且对 adapter 进行优化, 提取出 Loading 圈的相关业务逻辑, 封装到一个 Proxy-Adapter 中
 * 使得分页操作更加的灵活.
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class LoadMoreWithLoadingActivity extends BaseActivity implements LoadMoreView_1<Item_1> {

    @Inject
    LoadMorePresenter_1 presenter;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    RecyclerView.Adapter adapter;
    LoadingAdapterOptimize_2 proxyAdapter;

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
        presenter.loadMore(LoadMoreUtils_1.PAGE_SIZE, 0);
    }

    private void initRecyclerView() {
        adapter = new NormalAdapter_2();
        proxyAdapter = new LoadingAdapterOptimize_2(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getAppContext()));
        recycler.setAdapter(proxyAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getAppContext(), DividerItemDecoration.VERTICAL);
        recycler.addItemDecoration(itemDecoration);
        recycler.addOnScrollListener(new LoadMoreScrollListener_1(recycler) {
            @Override
            public void loadMore(int page, int count) {
                Toast.makeText(LoadMoreWithLoadingActivity.this.getAppContext(), "loading " + (page + 1), Toast.LENGTH_SHORT).show();
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
