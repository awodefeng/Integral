package com.xxun.fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.xxun.adpter.GoldRevenueAdapter;
import com.xxun.bean.IntergralBean;
import com.xxun.util.DateFormatUtils;
import com.xxun.xunintegral.MyApplication;
import com.xxun.xunintegral.R;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 金币收入界面
 *
 */

public class GoldRevenueFragment extends Fragment {
    private Context context;
    private ListView gRList;
    private LinearLayout no_get_gold;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gold_tab1, container, false);
        context = getActivity();
        inintview(view);
        initdata();
        return view;
    }

    private void initdata() {
        List<IntergralBean> igbeanList = new MyApplication().getIgbeanList();
        if(igbeanList!=null && igbeanList.size()!=0) {
            for (int i = 0; i < igbeanList.size(); i++)  {//外循环是循环的次数
                    if (igbeanList.get(i).getType() == 0) {
                        igbeanList.remove(i);
                        i--;
                }
        }
            List<IntergralBean> orderList = invertOrderList(igbeanList);
            GoldRevenueAdapter gRadpter = new GoldRevenueAdapter(orderList,getActivity());
            if(gRadpter!=null){
                gRList.setAdapter(gRadpter);
            }
            if(orderList.size()==0){
                no_get_gold.setVisibility(View.VISIBLE);
                gRList.setVisibility(View.GONE);
            }
        }else {
            no_get_gold.setVisibility(View.VISIBLE);
            gRList.setVisibility(View.GONE);
        }
    }
    //将List按照时间倒序排列 //取出数据中的时间戳，由String转换成Date
    private List<IntergralBean> invertOrderList(List<IntergralBean> igbeanList){
        Date d1;
          Date d2;
        IntergralBean igBean;
     //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<igbeanList.size()-1; i++) {
            for (int j = i + 1; j < igbeanList.size(); j++) {
                d1 = DateFormatUtils.strToDate(igbeanList.get(i).getTimestamp());
                d2 = DateFormatUtils.strToDate(igbeanList.get(j).getTimestamp());
                if (d1.before(d2)) {//如果队前日期靠前，调换顺序
                    igBean = igbeanList.get(i);
                    igbeanList.set(i, igbeanList.get(j));
                    igbeanList.set(j, igBean);
                }
            }
        }
        return igbeanList;
    }

    private void inintview(View view) {
        gRList = view.findViewById(R.id.goldRevenueList);
        no_get_gold = view.findViewById(R.id.no_get_gold);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
