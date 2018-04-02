package com.congguangzi.master_recycler.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.congguangzi.master_recycler.BuildConfig;
import com.congguangzi.master_recycler._1_loadmore.Item;

/**
 * 数据库访问控制类.
 *
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
@Database(entities = {
        Item.class
}, version = 1, exportSchema = false)
public abstract class MasterRecyclerViewDatabase extends RoomDatabase {

    private static MasterRecyclerViewDatabase INSTANCE;
    private static final Object LOCK_OBJ = new Object();

    public static MasterRecyclerViewDatabase getInstance(Context ctx) {
        synchronized (LOCK_OBJ) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(ctx, MasterRecyclerViewDatabase.class, BuildConfig.DB_NAME)
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract ItemDao getItemDao();
}
