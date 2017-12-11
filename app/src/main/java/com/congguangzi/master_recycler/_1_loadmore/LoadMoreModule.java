package com.congguangzi.master_recycler._1_loadmore;

import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@Module
public class LoadMoreModule {

    @Provides
    @PerActivity
    LoadMorePresenter_1 provideLoadMorePresenter(LoadMoreActivity activity) {
        return new LoadMorePresenter_1(activity);
    }
}
