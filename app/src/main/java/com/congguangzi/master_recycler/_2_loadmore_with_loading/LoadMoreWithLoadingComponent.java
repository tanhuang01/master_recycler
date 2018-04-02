package com.congguangzi.master_recycler._2_loadmore_with_loading;

import com.congguangzi.master_recycler.app.RecyclerAppComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@PerActivity
@Component(dependencies = RecyclerAppComponent.class,
        modules = LoadMoreWithLoadingModule.class)
public interface LoadMoreWithLoadingComponent {

    void inject(LoadMoreWithLoadingActivity activity);

    @Component.Builder
    interface Builder {
        LoadMoreWithLoadingComponent.Builder masterComponent(RecyclerAppComponent masterComponent);

        @BindsInstance
        LoadMoreWithLoadingComponent.Builder bindActivity(LoadMoreWithLoadingActivity activity);

        LoadMoreWithLoadingComponent build();
    }
}
