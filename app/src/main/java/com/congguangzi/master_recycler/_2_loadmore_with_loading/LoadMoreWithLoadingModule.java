package com.congguangzi.master_recycler._2_loadmore_with_loading;

import com.congguangzi.master_recycler._1_loadmore_recycler.LoadMorePresenter;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/6.
 */
@Module
public class LoadMoreWithLoadingModule {

    @PerActivity
    @Provides
    LoadMorePresenter provideLoadMorePresenter(LoadMoreWithLoadingActivity activity) {
        return new LoadMorePresenter(activity);
    }
}
