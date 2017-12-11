package com.congguangzi.master_recycler._1_loadmore;

import com.congguangzi.master_recycler.app.MasterApplicationComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@PerActivity
@Component(dependencies = MasterApplicationComponent.class, modules = LoadMoreModule.class)
public interface LoadMoreComponent {
    void inject(LoadMoreActivity activity);


    @Component.Builder
    interface Builder {
        LoadMoreComponent.Builder masterComponent(MasterApplicationComponent component);

        @BindsInstance
        LoadMoreComponent.Builder bindActivity(LoadMoreActivity activity);

        LoadMoreComponent build();
    }

}
