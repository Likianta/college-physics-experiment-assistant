package com.likianta.cpea;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Likianta_Dodoora on 2018/4/2 0002.
 */

public class DataActivity extends FragmentActivity implements View.OnClickListener {
    private final List<Fragment> fragments = new ArrayList<>();
    private DataPage1 PAGE1 = new DataPage1();
    private DataPage2 PAGE2 = new DataPage2();
    private DataPage3 PAGE3 = new DataPage3();
    private TextView tab1, tab2, tab3;
    private ViewPager dataPager;
    private int lastPagePosition = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity);
        
        initBindings();
        initViewPager();
        initListeners();
    }
    
    
    private void initBindings() {
        tab1 = findViewById(R.id.data_tab1);
        tab2 = findViewById(R.id.data_tab2);
        tab3 = findViewById(R.id.data_tab3);
        dataPager = findViewById(R.id.data_container);
        
        activateTabAppearance(0);
    }
    
    private void initViewPager() {
        fragments.add(PAGE1);
        fragments.add(PAGE2);
        fragments.add(PAGE3);
        DataPagerAdapter dataAdapter = new DataPagerAdapter(getSupportFragmentManager(), fragments);
        dataPager.setAdapter(dataAdapter);
    }
    
    private void initListeners() {
        tab1.setOnClickListener(this);
        tab2.setOnClickListener(this);
        tab3.setOnClickListener(this);
        
        dataPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                
            }
            
            @Override
            public void onPageSelected(int position) {
                /* 方法参数/变量的设计原则
                 * 1. 外来参数尽量抽象，避免使用具体的值
                 * 2. 当外来参数传到方法中时，方法需要立刻对该参数进行判断，比如类型是否正确，值是否为空等
                 * 3. 尽量避免使用公共变量，可以考虑将其转化为局部变量后使用再返回
                 * 4. 参数名尽量简洁，可以采用以下两种命名法（示例）：
                 *     1. 原名“身体健康状况报告表”，可以简化为“健况表”（优点是重名率低，缺点是难命名且第三方审查员不易看懂）
                 *     1.5. 典型示例：TextView tv = ...
                 *     2. 原名“身体健康状况报告表”，可以简化为“报表”（优点是易看懂，易命名，够简洁，缺点是重名率高）
                 *     2.5. 典型示例：TextView view = ... */
                activateTabAppearance(position);
                
                /* 功能逻辑
                 *
                 * 1. 检测页面变动，获得滑动到的新页面的位置position
                 * 2. activateTabAppearance: 新页面的tab控件设置为高亮的外观，同时非选中页面的tab变为非高亮状态
                 * 3. 如果新页面是由第一页切过来的，则必须立刻保存第一页中的所有表格数据到父活动的page1Data数组中
                 * 4. 完成以上操作，别忘了记录当前页面位置到lastPagePosition
                 * 5. 如果切换到了第一页，则注意调用Page1的activateSelector函数
                 * （因为实测发现每次切换回来的时候，Selector都会被重置；因此通过在这里主动激活指定的Selector来避免被重置的问题）
                 * 6. 如果切换到了第二页（坐标页面），则注意调用Page2的refreshChartData函数 */
                switch (position) {
                    case 0:
//                        loadPage1Tables(lastPagePosition);
                        break;
                    case 1:
                        // If you come from page1, you should make sure to hide the soft keyboard
                        if (lastPagePosition == 0) {
                            hideKeyboard();
                        }
                        PAGE2.refreshChartData(PAGE1.extractChartData());
                        break;
                    case 2:
                        
                        break;
                }
                
                lastPagePosition = position;
            }
            
            private void hideKeyboard() {
                InputMethodManager manager = (InputMethodManager) getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                if (manager != null && DataActivity.this.getCurrentFocus() != null) {
                    manager.hideSoftInputFromWindow(DataActivity.this.getCurrentFocus()
                            .getWindowToken(), 0);
                }
            }
            
            @Override
            public void onPageScrollStateChanged(int state) {
            
            }
        });
    }
    
    private void activateTabAppearance(int position) {
        TextView[] views = {tab1, tab2, tab3};
        for (int i = 0; i < views.length; i++) {
            if (i == position) {
                views[i].setTextColor(this.getResources().getColor(R.color.colorTabSelected));
                views[i].getPaint().setFakeBoldText(true);
            } else {
                views[i].setTextColor(this.getResources().getColor(R.color.colorTabUnselected));
                views[i].getPaint().setFakeBoldText(false);
            }
        }
    }
    
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.data_tab1:
                dataPager.setCurrentItem(0);
                break;
            case R.id.data_tab2:
                dataPager.setCurrentItem(1);
                break;
            case R.id.data_tab3:
                dataPager.setCurrentItem(2);
                break;
        }
    }
}
