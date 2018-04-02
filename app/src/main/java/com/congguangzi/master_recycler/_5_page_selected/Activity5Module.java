package com.congguangzi.master_recycler._5_page_selected;

import com.congguangzi.master_recycler._1_loadmore.LoadMorePresenter;
import com.congguangzi.master_recycler.app.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author congguangzi (congspark@163.com) 2018/3/29.
 */
@Module
public class Activity5Module {

    @PerActivity
    @Provides
    LoadMorePresenter provideLoadMorePresenter_1(Activity5 activity5) {
        return new LoadMorePresenter(activity5);
    }
}
