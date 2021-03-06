package com.congguangzi.master_recycler._3_loadmore_grid_with_loading;

import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/8.
 */
@Module
public class LoadMoreGridModule {

    @PerActivity
    @Provides
    LoadMorePresenter provideLoadMorePresenter_1(LoadMoreGridActivity activity) {
        return new LoadMorePresenter(activity);
    }
}
