package com.congguangzi.master_recycler._1_loadmore;

import java.util.ArrayList;
import java.util.List;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2017/11/23.
 */
public class LoadMoreUtils_1 {

    public static final int PAGE_SIZE = 8;

    public static List<Item_1> generateSet() {
        List<Item_1> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            items.add(new Item_1("title:" + i, "detail:" + i));
        }
        return items;
    }
}
