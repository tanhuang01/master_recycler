package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.congguangzi.master_recycler.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 简介: 滑动到底端加载更多 recyclerView
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
public class LoadMoreRecyclerActivity extends AppCompatActivity implements LoadMoreView<Item> {

    LoadMorePresenter presenter;

    @BindView(R.id.recycler)
    RecyclerView recycleView;

    ItemAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._1_load_more_recycler_layout);
        ButterKnife.bind(this);
        presenter = new LoadMorePresenter(this);
        presenter.loadMore(LoadMoreUtils.PAGE_SIZE, 0);
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new ItemAdapter();
        recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycleView.setAdapter(adapter);
        recycleView.addOnScrollListener(new LoadMoreScrollListener(recycleView) {
            @Override
            public void loadMore(int page, int count) {
                ((LoadMore) adapter).setLoading(true);
                presenter.loadMore(count, count * page);
            }
        });
    }


    @Override
    public void loadedMore(List<Item> set) {
        adapter.loadMore(set);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
