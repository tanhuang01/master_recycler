package com.congguangzi.master_recycler._5_page_selected;

import com.congguangzi.master_recycler.app.PerActivity;
import com.congguangzi.master_recycler.app.RecyclerAppComponent;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author congguangzi (congspark@163.com) 2018/3/29.
 */
@PerActivity
@Component(dependencies = RecyclerAppComponent.class,
        modules = Activity5Module.class)
public interface Activity5Component {

    void inject(Activity5 activity5);

    @Component.Builder
    interface Builder {
        Activity5Component.Builder masterComponent(RecyclerAppComponent component);

        @BindsInstance
        Activity5Component.Builder bindActivity(Activity5 activity5);

        Activity5Component build();
    }


}
