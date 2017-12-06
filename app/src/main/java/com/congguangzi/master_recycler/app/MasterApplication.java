package com.congguangzi.master_recycler.app;

import android.app.Application;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */

public class MasterApplication extends Application {

    MasterApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerMasterApplicationComponent.builder()
                .bindApplication(this)
                .masterModule(new MasterModule(this))
                .build();
        appComponent.inject(this);
    }

    public MasterApplicationComponent getAppComponent() {
        return appComponent;
    }
}
