package com.xxun.xunintegral;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xxun.fragment.GoldExchangeFragment;
import com.xxun.fragment.GoldRevenueFragment;
import com.xxun.view.CustomScrollViewPager;
import com.xxun.view.RedCursorView;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.app.FragmentActivity;

public class GoldRecordActivity extends FragmentActivity implements OnPageChangeListener,View.OnClickListener{
    private android.support.v4.app.FragmentManager mFragmentManager;
    private TextView gold_revenue,gold_exchange;
   // private View view1;
    //声明四个Tab分别对应的Fragment
    private GoldRevenueFragment revenueFragment; //金币收入
    private GoldExchangeFragment exchangeFragment; //金币兑换
    //装载Fragment的集合
    private ArrayList<Fragment> mFragments;
    //适配器
    private FragmentPagerAdapter mAdpter;
    private RedCursorView rdview;
    private CustomScrollViewPager myviewpager;
    //装载Fragment的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gold_record);
        initview();
	if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        initEvents();//初始化事件
        initData();
        selectTab(0);//默认选中第一个Tab
    }
    private void initview() {
        gold_revenue = (TextView)findViewById(R.id.gold_revenue);
        gold_exchange= (TextView)findViewById(R.id.gold_exchange);
        // view1 = findViewById(R.id.view1);
        rdview = (RedCursorView)findViewById(R.id.red_view);
        myviewpager =(CustomScrollViewPager)findViewById(R.id.id_viewpager);
    }
    private void initEvents() {
        gold_revenue.setOnClickListener(this);
        gold_exchange.setOnClickListener(this);
    }
    private void initData() {
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new GoldRevenueFragment());
        mFragments.add(new GoldExchangeFragment());
	//mFragmentManager =getSupportFragmentManager();
        mAdpter = new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        myviewpager.setAdapter(mAdpter);
	myviewpager.setOnPageChangeListener(this);
       // myviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
          
      //  });
    }

 	   @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                rdview.setXY(position, positionOffset);
            }
            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                myviewpager.setCurrentItem(position);
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }




    private void selectTab(int i) {
        //根据点击的Tab设置对应的TextView为白色
        switch (i){
            case 0:
                gold_revenue.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                gold_exchange.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    //设置当前点击的Tab所对应的页面
        myviewpager.setCurrentItem(i);
    }


    //将两个TextView设置为灰色
    private void resetattribute() {
        gold_revenue.setTextColor(getResources().getColor(R.color.no_finsh_text));
        gold_exchange.setTextColor(getResources().getColor(R.color.no_finsh_text));
    }

    @Override
    public void onClick(View v) {
        resetattribute();
        switch (v.getId()){
            case R.id.gold_revenue:
                selectTab(0);
                break;
            case R.id.gold_exchange:
                selectTab(1);
                break;
        }
    }
}
