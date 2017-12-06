package com.congguangzi.master_recycler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockApplication;

import com.congguangzi.master_recycler.app.MasterApplication;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getMasterApplicationComponent());
    }

    abstract protected void inject(MasterApplicationComponent appComponent);

    protected MasterApplicationComponent getMasterApplicationComponent() {
        return ((MasterApplication) getApplication()).getAppComponent();
    }

}
