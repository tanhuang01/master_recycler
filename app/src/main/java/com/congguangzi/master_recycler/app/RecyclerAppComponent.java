package com.congguangzi.master_recycler.app;

import com.congguangzi.master_recycler.db.MasterRecyclerViewDatabase;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@App
@Component(modules = MasterModule.class)
public interface RecyclerAppComponent {

    void inject(MasterApplication application);

    MasterRecyclerViewDatabase getDatabase();

    @Component.Builder
    interface Builder {
        @BindsInstance
        RecyclerAppComponent.Builder bindApplication(MasterApplication application);

        RecyclerAppComponent.Builder masterModule(MasterModule module);

        RecyclerAppComponent build();
    }
}
