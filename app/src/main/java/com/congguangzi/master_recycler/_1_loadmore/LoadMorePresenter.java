package com.congguangzi.master_recycler._1_loadmore;

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
        this.loadMoreRepository = new LoadMoreRepository(view.getAppContext(), this);
    }

    public void initDataBase(List<Item> items) {
        loadMoreRepository.initDataBase(items);
    }

    /**
     * 加载更多数据.
     *
     * @param offset 数据库查询 offset.
     * @param limit  数据库查询 limit.
     */
    public void loadMore(int offset, int limit) {
        loadMoreRepository.loadMoreItem(offset, limit);
    }

    /**
     *
     * @param offset
     * @param limit
     * @param delay 加载延迟时间
     */
    public void loadMore(int offset, int limit, int delay) {
        loadMoreRepository.loadMoreItem(offset, limit, delay);
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
