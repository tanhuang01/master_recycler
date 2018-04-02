package com.congguangzi.master_recycler;

import com.congguangzi.master_recycler.app.PerActivity;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2018/4/2.
 */
@PerActivity
@Component(modules = MainModule.class,
        dependencies = RecyclerAppComponent.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    @Component.Builder
    interface Builder {

        MainActivityComponent.Builder recyclerComponent(RecyclerAppComponent appComponent);

        @BindsInstance
        MainActivityComponent.Builder bindInstance(MainActivity mainActivity);

        MainActivityComponent build();
    }
}
