package com.xxun.fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.xxun.adpter.GoldExchangeAdapter;
import com.xxun.bean.IntergralBean;
import com.xxun.util.DateFormatUtils;
import com.xxun.xunintegral.MyApplication;
import com.xxun.xunintegral.R;
import java.util.Date;
import java.util.List;

public class GoldExchangeFragment extends Fragment {
    private ListView lv_exchange;
    private LinearLayout mo_gold_exchange;
    private ImageView go_exchange;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gold_tab2, container, false);
        initview(view);
        inidata();
        return view;
    }

    private void inidata() {
        List<IntergralBean> igbeanList = new MyApplication().getIgbeanList();
        if(igbeanList!=null && igbeanList.size()!=0) {
            for (int i = 0; i < igbeanList.size(); i++)  {//外循环是循环的次数
                    if (igbeanList.get(i).getType() == 1) {
                        igbeanList.remove(i);
                        i--;
                    }
            }
            List<IntergralBean> orderList = invertOrderList(igbeanList);
            GoldExchangeAdapter exchangeAdapter = new GoldExchangeAdapter(getActivity(),orderList);
            if(exchangeAdapter!=null){
                lv_exchange.setAdapter(exchangeAdapter);
            }
            if(orderList.size()==0){
                mo_gold_exchange.setVisibility(View.VISIBLE);
                lv_exchange.setVisibility(View.GONE);
            }
        }else {
            mo_gold_exchange.setVisibility(View.VISIBLE);
            lv_exchange.setVisibility(View.GONE);
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
    private void initview(View view) {
        lv_exchange = view.findViewById(R.id.lv_exchange);
        mo_gold_exchange = view.findViewById(R.id.mo_gold_exchange);
        go_exchange = view.findViewById(R.id.go_exchange);
    }
}
