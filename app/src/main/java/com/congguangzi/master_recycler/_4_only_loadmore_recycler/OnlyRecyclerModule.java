package com.congguangzi.master_recycler._4_only_loadmore_recycler;

import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter_1;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2017/12/12.
 */
@Module
public class OnlyRecyclerModule {

    @PerActivity
    @Provides
    LoadMorePresenter_1 provideLoadMorePresenter(OnlyRecyclerActivity activity) {
        return new LoadMorePresenter_1(activity);
    }
}
