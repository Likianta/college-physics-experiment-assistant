package com.likianta.cpea;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/4/3 0003.
 */

public class DataPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    
    public DataPagerAdapter(FragmentManager manager, List<Fragment> fragments) {
        super(manager);
        this.fragments = fragments;
    }
    
    /*
        FragmentPagerAdapter看起来比较陌生，但与PagerAdapter进行比较就会发现，二者是非常相似的
        首先阅读这篇文章，你会非常轻松地理解PagerAdapter（至少知道它的用法）：http://sq986.top/share?data
        =MTE0MDI2MTUyMjgwNDc2Mjk2MTc3NTc5OCZVQlh1eQ==

        然后阅读这篇文章，并注意上一篇文章相联系，你就会发现FragmentPagerAdapter并没有想象中那么难（因为重写的方法就那几个）：https://blog.csdn
        .net/harvic880925/article/details/38660861

        该教程的作者一向喜欢使用简单的例子帮助我们探索Android开发，希望大家也能向他学习
    */
    
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
    
    @Override
    public int getCount() {
        return fragments.size();
    }
    
    /*@Override
    public Object instantiateItem(ViewGroup container, int position) {
        DataPage2 fragment = (DataPage2) super.instantiateItem(container, position);
        return fragment;
    }*/
    
}
