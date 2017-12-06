package com.congguangzi.master_recycler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreRecyclerActivity;
import com.congguangzi.master_recycler._2_loadmore_recycler_with_loading.LoadMoreRecyclerWidthLoadingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_load_more)
    Button bt;

    @BindView(R.id.bt_load_more_with_loading)
    Button bt_load_more_with_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_load_more)
    void startLoadMoreRecyclerActivity() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreRecyclerActivity.class);
        startActivity(intent);
    }

    void startLoadMoreRecyclerWithLoadingActivity() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreRecyclerWidthLoadingActivity.class);
        startActivity(intent);
    }

}
