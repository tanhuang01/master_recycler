package com.congguangzi.master_recycler._3_loadmore_grid_with_loading;

import com.congguangzi.master_recycler.app.RecyclerAppComponent;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */
@PerActivity
@Component(dependencies = RecyclerAppComponent.class,
        modules = LoadMoreGridModule.class)
public interface LoadMoreGridComponent {

    void inject(LoadMoreGridActivity activity);

    @Component.Builder
    interface Builder {
        LoadMoreGridComponent.Builder masterComponent(RecyclerAppComponent component);

        @BindsInstance
        LoadMoreGridComponent.Builder bindActivity(LoadMoreGridActivity activity);

        LoadMoreGridComponent build();
    }

}
