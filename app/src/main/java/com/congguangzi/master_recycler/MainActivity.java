package com.congguangzi.master_recycler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.congguangzi.master_recycler._1_loadmore.Item;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreActivity;
import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreUtils;
import com.congguangzi.master_recycler._1_loadmore.LoadMoreView;
import com.congguangzi.master_recycler._2_loadmore_with_loading.LoadMoreWithLoadingActivity;
import com.congguangzi.master_recycler._3_loadmore_grid_with_loading.LoadMoreGridActivity;
import com.congguangzi.master_recycler._4_only_loadmore_recycler.OnlyRecyclerActivity;
import com.congguangzi.master_recycler._5_page_selected.Activity5;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements LoadMoreView<Item> {

    @BindView(R.id.bt_load_more)
    Button bt;

    @BindView(R.id.bt_load_more_with_loading)
    Button bt_load_more_with_loading;

    @BindView(R.id.bt_load_more_grid)
    Button bt_grid_loading;

    @BindView(R.id.bt_only_page_recycler)
    Button bt_only_recycler;

    @Inject
    LoadMorePresenter presenter;

    @Override
    protected void inject(RecyclerAppComponent appComponent) {
        DaggerMainActivityComponent.builder()
                .recyclerComponent(appComponent)
                .bindInstance(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 查找第一条数据.
        presenter.loadMore(0, 1, 0);
    }

    @OnClick(R.id.bt_load_more)
    void startLoadMoreActivity() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_load_more_with_loading)
    void startLoadMoreWithLoadingActivity() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreWithLoadingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_load_more_grid)
    void startLoadMoreGridActivity() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreGridActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.bt_only_page_recycler)
    void startOnlyPageActivity() {
        Intent intent = new Intent(getApplicationContext(), OnlyRecyclerActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.bt_selected_paged)
    void startPageAndSelectedActivity() {
        Intent intent = new Intent(getApplicationContext(), Activity5.class);
        startActivity(intent);
    }

    @Override
    public void loadedMore(List<Item> set) {
        Log.w("activity_main", "original data size: " + set.size());
        if (set.isEmpty()) {
            presenter.initDataBase(LoadMoreUtils.generateSet());
        }
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
