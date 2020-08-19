package com.lib.base.view.viewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020-08-19
 * 简 述：<功能简述>
 */
public class PageViewAdapter<T, V extends View> extends PagerAdapter {

    private List<T> list;
    private IPageViewAdapter<T, V> iPageViewAdapter;

    private List<V> vList;

    public PageViewAdapter(List<T> list, @NonNull IPageViewAdapter<T, V> iPageViewAdapter) {
        this.list = list;
        this.iPageViewAdapter = iPageViewAdapter;
        vList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        T t = list.get(position);
        V v = vList.size() > 0 ? vList.remove(0) : null;
        return iPageViewAdapter.createView(t, position, v);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        if (object instanceof View) {
            container.removeView((View) object);
            vList.add((V) object);
        }

    }
}
