package com.congguangzi.master_recycler._2_loadmore_recycler_with_loading;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.congguangzi.master_recycler.BaseActivity;
import com.congguangzi.master_recycler.R;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

public class LoadMoreRecyclerWidthLoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more_recycler_width_loading);
    }

    @Override
    protected void inject(MasterApplicationComponent appComponent) {

    }
}
