package com.congguangzi.master_recycler._1_loadmore_recycler;

import java.util.ArrayList;
import java.util.List;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */
public class LoadMoreUtils {

    public static final int PAGE_SIZE = 8;

    public static List<Item> generateSet() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(new Item("title:" + i, "detail:" + i));
        }
        return items;
    }
}
