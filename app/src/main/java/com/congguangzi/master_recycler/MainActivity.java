package com.congguangzi.master_recycler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMoreRecyclerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.load_more)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.load_more)
    void statLoadMoreRecyclerView() {
        Intent intent = new Intent(getApplicationContext(), LoadMoreRecyclerActivity.class);
        startActivity(intent);
    }
}
