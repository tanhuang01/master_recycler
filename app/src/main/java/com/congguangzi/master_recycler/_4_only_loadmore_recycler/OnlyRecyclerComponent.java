package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import com.congguangzi.master_recycler.app.RecyclerAppComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/12.
 */
@PerActivity
@Component(dependencies = RecyclerAppComponent.class,
        modules = OnlyRecyclerModule.class)
public interface OnlyRecyclerComponent {

    void inject(OnlyRecyclerActivity activity);

    @Component.Builder
    interface Builder {
        OnlyRecyclerComponent.Builder masterComponent(RecyclerAppComponent masterComponent);

        @BindsInstance
        OnlyRecyclerComponent.Builder bindActivity(OnlyRecyclerActivity activity);

        OnlyRecyclerComponent build();
    }
}
