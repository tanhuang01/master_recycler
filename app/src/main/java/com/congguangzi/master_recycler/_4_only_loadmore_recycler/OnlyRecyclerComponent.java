package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import com.congguangzi.master_recycler.app.MasterApplication;
import com.congguangzi.master_recycler.app.MasterApplicationComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/12.
 */
@PerActivity
@Component(dependencies = MasterApplicationComponent.class,
        modules = OnlyRecyclerModule.class)
public interface OnlyRecyclerComponent {

    void inject(OnlyRecyclerActivity activity);

    @Component.Builder
    interface Builder {
        OnlyRecyclerComponent.Builder masterComponent(MasterApplicationComponent masterComponent);

        @BindsInstance
        OnlyRecyclerComponent.Builder bindActivity(OnlyRecyclerActivity activity);

        OnlyRecyclerComponent build();
    }
}
