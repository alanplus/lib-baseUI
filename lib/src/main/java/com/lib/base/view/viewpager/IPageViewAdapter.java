package com.lib.base.view.viewpager;

import android.view.View;

/**
 * @author Alan
 * 时 间：2020-08-19
 * 简 述：<功能简述>
 */
public interface IPageViewAdapter<T, V extends View> {

    V createView(T t, int position, V v);
}
