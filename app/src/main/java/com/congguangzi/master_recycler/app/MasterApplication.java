package com.congguangzi.master_recycler.app;

import android.app.Application;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */

public class MasterApplication extends Application {

    RecyclerAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerRecyclerAppComponent.builder()
                .bindApplication(this)
                .masterModule(new MasterModule(this))
                .build();
        appComponent.inject(this);
    }

    public RecyclerAppComponent getAppComponent() {
        return appComponent;
    }
}
