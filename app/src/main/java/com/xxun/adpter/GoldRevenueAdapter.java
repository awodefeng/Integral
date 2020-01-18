package com.xxun.adpter;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xxun.bean.IntergralBean;
import com.xxun.xunintegral.MyApplication;
import com.xxun.xunintegral.R;
import java.util.List;
import java.util.Map;

public class GoldRevenueAdapter extends BaseAdapter {
    private List<IntergralBean> intergralBeans;
    private Context context;

    public GoldRevenueAdapter(List<IntergralBean> intergralBeans, Context context) {
        this.intergralBeans = intergralBeans;
        this.context = context;
    }

    @Override
    public int getCount() {
        return intergralBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return intergralBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_gold_tab1,null);
            holder.open_box = convertView.findViewById(R.id.open_box);
            holder.tv_datastamp = convertView.findViewById(R.id.tv_datastamp);
            holder.get_gold = convertView.findViewById(R.id.get_gold);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map<Integer, String> map = new MyApplication().getruleNametoId();
        holder.open_box.setText(map.get(intergralBeans.get(position).getModuleid()));
        String timestamp = intergralBeans.get(position).getTimestamp();
        if(!TextUtils.isEmpty(timestamp)){
            //截取后14位
            String substring1 = timestamp.substring(timestamp.length() - 14);
            holder.tv_datastamp.setText(substring1);
        }
        holder.get_gold.setText("+"+intergralBeans.get(position).getGetgold()+"");
        return convertView;
    }
    class ViewHolder{
        TextView open_box,tv_datastamp,get_gold;
    }

}
