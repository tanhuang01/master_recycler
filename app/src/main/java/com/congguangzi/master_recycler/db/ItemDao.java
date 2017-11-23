package com.congguangzi.master_recycler.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import com.congguangzi.master_recycler._1_loadmore_recycler.Item;

import java.util.List;

/**
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
@Dao
public interface ItemDao {

    @Query("select * from item")
    List<Item> getAllItems();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(@NonNull Item... value);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(@NonNull List<Item> value);

    @Delete
    int delete(@NonNull Item... value);

    @Query("select * from item limit (:limit) offset (:offset)")
    List<Item> getSpecialPageItems(int limit, int offset);
}
