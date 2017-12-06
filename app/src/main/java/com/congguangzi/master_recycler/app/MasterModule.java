package com.congguangzi.master_recycler.app;

import com.congguangzi.master_recycler.db.MasterRecyclerViewDatabase;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@Module
public class MasterModule {

    public MasterModule(MasterApplication application) {
    }

    @App
    @Provides
    public MasterRecyclerViewDatabase provideDataBase(MasterApplication application) {
        return MasterRecyclerViewDatabase.getInstance(application);
    }
}
