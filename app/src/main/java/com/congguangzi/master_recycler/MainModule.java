package com.congguangzi.master_recycler;

import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2018/4/2.
 */
@Module
public class MainModule {

    @PerActivity
    @Provides
    public LoadMorePresenter provideLoadMorePresenter(MainActivity mainActivity) {
        return new LoadMorePresenter(mainActivity);
    }
}
