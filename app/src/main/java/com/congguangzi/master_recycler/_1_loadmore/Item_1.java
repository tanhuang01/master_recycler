package com.congguangzi.master_recycler._1_loadmore;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author congguangzi (congspark@163.com) 2017/11/21.
 */
@Entity(tableName = "item")
public class Item_1 {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String detail;

    public Item_1(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    private Item_1(Builder builder) {
        id = builder.id;
        title = builder.title;
        detail = builder.detail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                '}' + '\n';
    }

    public static final class Builder {
        long id;
        String title;
        String detail;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Item_1 build(Builder builder) {
            return new Item_1(builder);
        }
    }
}
