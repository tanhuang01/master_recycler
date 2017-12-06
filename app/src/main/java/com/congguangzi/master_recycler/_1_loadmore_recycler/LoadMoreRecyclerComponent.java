package com.congguangzi.master_recycler._1_loadmore_recycler;

import com.congguangzi.master_recycler.app.MasterApplicationComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@PerActivity
@Component(dependencies = MasterApplicationComponent.class, modules = LoadMoreRecyclerModule.class)
public interface LoadMoreRecyclerComponent {
    void inject(LoadMoreRecyclerActivity activity);


    @Component.Builder
    interface Builder {
        LoadMoreRecyclerComponent.Builder masterComponent(MasterApplicationComponent component);

        @BindsInstance
        LoadMoreRecyclerComponent.Builder bindActivity(LoadMoreRecyclerActivity activity);

        LoadMoreRecyclerComponent build();
    }

}
