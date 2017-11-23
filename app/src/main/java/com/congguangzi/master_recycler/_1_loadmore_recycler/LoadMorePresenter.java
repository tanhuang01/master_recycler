package com.congguangzi.master_recycler._1_loadmore_recycler;

import java.util.List;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/22.
 */
public class LoadMorePresenter {

    private LoadMoreRepository loadMoreRepository;

    private LoadMoreView<Item> view;

    public LoadMorePresenter(LoadMoreView view) {
        this.view = view;
        this.loadMoreRepository = new LoadMoreRepository(view.getContext(), this);
    }

    public void initDataBase(List<Item> items) {
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
    public void loadedMore(List<Item> set) {
        view.loadedMore(set);
    }
}
