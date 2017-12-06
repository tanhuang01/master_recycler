package com.congguangzi.master_recycler.app;

import com.congguangzi.master_recycler.db.MasterRecyclerViewDatabase;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@App
@Component(modules = MasterModule.class)
public interface MasterApplicationComponent {

    void inject(MasterApplication application);

    MasterRecyclerViewDatabase getDatabase();

    @Component.Builder
    interface Builder {
        @BindsInstance
        MasterApplicationComponent.Builder bindApplication(MasterApplication application);

        MasterApplicationComponent.Builder masterModule(MasterModule module);

        MasterApplicationComponent build();
    }
}
