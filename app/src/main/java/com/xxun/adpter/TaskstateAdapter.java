package com.xxun.adpter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xxun.bean.TaskStateBean;
import com.xxun.xunintegral.R;
import java.util.List;

public class TaskstateAdapter extends BaseAdapter {
    private List<TaskStateBean> tsList;
    private Context context;

    public TaskstateAdapter(Context context, List<TaskStateBean> tsList) {
        this.context = context;
        this.tsList = tsList;
    }

    @Override
    public int getCount() {
        return tsList.size();
    }

    @Override
    public Object getItem(int position) {
        return tsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.every_day_item, null);
            holder.finsh_number = (TextView)convertView.findViewById(R.id.finsh_number);
            holder.task_name = (TextView)convertView.findViewById(R.id.task_name);
            holder.finsh_get_gold = (TextView)convertView.findViewById(R.id.finsh_get_gold);
            holder.tv_stsk_number = (TextView)convertView.findViewById(R.id.tv_stsk_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //积分规则
        if (tsList.get(position).getModuleid() != null) {
            //设置积分
            //setGold(holder, tsList.get(position));
            holder.finsh_number.setVisibility(View.GONE);
            holder.finsh_get_gold.setVisibility(View.VISIBLE);
            holder.finsh_get_gold.setText("+" + tsList.get(position).getGetgold());
            holder.tv_stsk_number.setText("1/1");
        }
        holder.task_name.setText(tsList.get(position).getTaskName());
        return convertView;
    }


    private void setUiupdate(ViewHolder holder, TaskStateBean taskStateBean1) {
        holder.finsh_number.setVisibility(View.GONE);
        holder.finsh_get_gold.setVisibility(View.VISIBLE);
        holder.finsh_get_gold.setText("+" + taskStateBean1.getGetgold());
        holder.tv_stsk_number.setText("1/1");
    }

    class ViewHolder {
        TextView task_name, tv_stsk_number;
        TextView finsh_number;
        TextView finsh_get_gold;


    }
}
