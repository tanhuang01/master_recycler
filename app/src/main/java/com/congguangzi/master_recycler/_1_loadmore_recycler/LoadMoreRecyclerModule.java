package com.congguangzi.master_recycler._1_loadmore_recycler;

import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@Module
public class LoadMoreRecyclerModule {

    @Provides
    @PerActivity
    LoadMorePresenter provideLoadMorePresenter(LoadMoreRecyclerActivity activity) {
        return new LoadMorePresenter(activity);
    }
}
