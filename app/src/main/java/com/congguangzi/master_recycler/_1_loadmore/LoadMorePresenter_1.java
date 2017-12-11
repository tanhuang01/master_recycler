package com.congguangzi.master_recycler._1_loadmore;

import java.util.List;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/22.
 */
public class LoadMorePresenter_1 {

    private LoadMoreRepository_1 loadMoreRepository;

    private LoadMoreView_1<Item_1> view;

    public LoadMorePresenter_1(LoadMoreView_1 view) {
        this.view = view;
        this.loadMoreRepository = new LoadMoreRepository_1(view.getAppContext(), this);
    }

    public void initDataBase(List<Item_1> items) {
        loadMoreRepository.initDataBase(items);
    }

    /**
     * 加载更多数据.
     *
     * @param limit  数据库查询 limit.
     * @param offset 数据库查询 offset.
     */
    public void loadMore(int limit, int offset) {
        loadMoreRepository.loadMoreItem(limit, offset);
    }

    /**
     * 加载完成
     *
     * @param set
     */
    public void loadedMore(List<Item_1> set) {
        view.loadedMore(set);
    }
}
