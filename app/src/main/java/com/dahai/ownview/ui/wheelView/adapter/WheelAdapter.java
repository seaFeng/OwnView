package com.dahai.ownview.ui.wheelView.adapter;

/**
 * Created by 张海洋 on 2018-06-08.
 */
public interface WheelAdapter<T> {
    /**
     *  获取item的数量
     * @return
     */
    int getItemsCount();

    /**
     *  获取指定条目
     * @param index
     * @return
     */
    T getItem(int index);

    /**
     *  获取对象的索引。
     * @param o
     * @return
     */
    int indexOf(T o);
}
