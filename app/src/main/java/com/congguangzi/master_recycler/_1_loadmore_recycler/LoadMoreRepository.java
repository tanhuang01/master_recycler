package com.congguangzi.master_recycler._1_loadmore_recycler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.congguangzi.master_recycler.baseRepository;
import com.congguangzi.master_recycler.db.ItemDao;
import com.congguangzi.master_recycler.db.MasterRecyclerViewDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/22.
 */
class LoadMoreRepository implements baseRepository {

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    MasterRecyclerViewDatabase database;

    ItemDao dao;

    LoadMorePresenter presenter;

    LoadMoreRepository(Context context, LoadMorePresenter presenter) {
        this.presenter = presenter;
        this.database = MasterRecyclerViewDatabase.getInstance(context);
        dao = database.getItemDao();
    }

    /**
     * 初始化数据库, 向其中插入若干条数据.
     *
     * @param items
     */
    void initDataBase(final List<Item> items) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                List<Long> rows = dao.insert(items);
                e.onNext(rows.size());

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void loadMoreItem(final int limit, final int offset) {
        Observable.create(new ObservableOnSubscribe<List<Item>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Item>> e) throws Exception {
                // 增加 1s 的延时.
                TimeUnit.MILLISECONDS.sleep(500);
                List<Item> items = dao.getSpecialPageItems(limit, offset);
                e.onNext(items);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Item>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Item> items) {
                        presenter.loadedMore(items);
                        Log.w("db", "item size: " + items.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void release() {
        compositeDisposable.dispose();
    }
}
