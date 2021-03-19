package com.xxun.xunintegral;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.xxun.BaseActivity;
import com.xxun.adpter.TaskstateAdapter;
import com.xxun.bean.IntergralBean;
import com.xxun.bean.TaskStateBean;
import com.xxun.dialog.LuckyBoxDiolog;
import com.xxun.dialog.TaskFinshhintDialog;
import com.xxun.util.DateFormatUtils;
import com.xxun.util.SPUtil;
import com.xxun.view.GifTempletView;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import java.util.Set;
import android.database.ContentObserver;
import android.provider.Settings;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "xunintegralMainActivity";
    public static boolean isClickLuckyBag = true;
    private boolean isFirst =false; //默认没有打开宝箱
    private ListView mylistview;
    private ArrayList<TaskStateBean> tsList= new ArrayList<TaskStateBean>();
    private LinearLayout gold_payments;
    TaskStateBean tsBean;
    String arrs[] = {"开宝箱     ","计步达标","跑一公里",/*"刷公交     ",*/"添加好友","拍照识字","拍照识图",
        "使用小爱","发朋友圈","赞朋友圈","终极宝箱","升级固件",/*"评朋友圈",*/"英语学习","英语测试"/*,"终极宝箱"*/};
    private ArrayList<String> taskname= new ArrayList<String>();
    private GifTempletView  gifview;
    private ImageView Iv_open;
    private TaskstateAdapter adapter;
    private int sumScore =0;
    private TextView total_score;
    private MyApplication myApplication;
    private int sqtotalExp;
    private int openfinalbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // SPUtil.clear(this,Canstance.SharedPreferencesName);
       //0为true 1为false
        myApplication = new MyApplication();

       // isFirst =  (int)SPUtil.get(this,Canstance.SharedPreferencesName,Canstance.ISFIRSTENTERDAILY,0) == 0 ? true : false;  //幸运经验
        initNormlBox();
        initview();
        //initProvider();
        initOnclik();
		ListenTotalExpChange();
    }

    @Override
    protected void onResume() {
		super.onResume();
        initdata();
        initsocre();
        isAllTashFinsih();
		initTotalExp();
	}

	private void initNormlBox(){
        if(myApplication.isFirstEnterToday()){
            SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName, Canstance.ISFIRSTENTERDAILY, 0);
            SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName,Canstance.isoBoxflag,0);
        }
        int isfrtst = Integer.parseInt(SPUtil.get(this, Canstance.SharedPreferencesName, Canstance.ISFIRSTENTERDAILY, 0).toString());
        if(isfrtst == 0){
            isFirst = true;
        }else {
            isFirst = false;
        }
    }

    private void initTotalExp(){
        total_score.setText(myApplication.getTotalExp()+"");
    }

    private void initProvider() {
        Uri uri_user = Uri.parse("content://" + Canstance.AUTOHORITY + "/intergral");
        if(myApplication.isFirstEnterToday()){
            List<IntergralBean> igbeanList = myApplication.getTodayIgbeanList();
            if(igbeanList!=null && igbeanList.size()!=0) {
                for (int i = 0; i < igbeanList.size(); i++) {//ÍâÑ­»·ÊÇÑ­»·µÄŽÎÊý
                    if(!DateFormatUtils.isToday(igbeanList.get(i).getTimestamp())){
                        Log.i(TAG, "initProvider igbeanList.get(i) modle = "+igbeanList.get(i).getModuleid());
                        MainActivity.this.getContentResolver().delete(uri_user, "moduleid = ?", new String[]{String.valueOf(igbeanList.get(i).getModuleid())});
                    }
                }
            }
        }
    }

  private void isAllTashFinsih() {
        //判断任务完成情况  是否开启终极宝箱
        if(Integer.parseInt(SPUtil.get(MainActivity.this, Canstance.SharedPreferencesName,Canstance.isoBoxflag,0).toString())==0 ){
            Taskcompletionstatus();
        }
        openfinalbox =Integer.parseInt(SPUtil.get(MainActivity.this, Canstance.SharedPreferencesName, Canstance.isopenFinalBox, 0).toString());
        Log.i(TAG,"openfinalbox is " + openfinalbox);
        if(openfinalbox==1){
            //开启终极宝箱
            isClickLuckyBag=true;
            gifview.setVisibility(View.VISIBLE);
            Iv_open.setVisibility(View.GONE);
        }
    }

   private void Taskcompletionstatus() {
        Map<Integer, String> preferencesParameter = myApplication.getPreferencesParameter();
       Set<Integer> keys = preferencesParameter.keySet();
       for(Integer key:keys){
            String parmtertype = preferencesParameter.get(key);
            int anInt = getAnInt(parmtertype);
           Log.i(TAG, "Taskcompletionstatus parmtertype = " + parmtertype+" anInt = "+anInt);
            if(anInt ==1){
                SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName, Canstance.isopenFinalBox, 1);
            }else {
                //只要有一个不是  改变数字并退出循环
                SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName, Canstance.isopenFinalBox, 0);
                Log.i(TAG,"Taskcompletionstatus not parmtertype is " + parmtertype);
                break;
            }
        }
    }

   private int getAnInt(String type) {
        return Integer.parseInt(SPUtil.get(this, Canstance.SharedPreferencesName, type, 0).toString());
    }

    private void initsocre() {
        final List<IntergralBean> igbeanList = myApplication.getTodayIgbeanList();
        if(igbeanList!=null && igbeanList.size()!=0) {
            Log.i("duanjinqian", "igbeanList.size()==" + igbeanList.size());
        }
        gifview.setMovieResource(R.drawable.boxgif);
        if(igbeanList!=null && igbeanList.size()!=0){
            Map<Integer, String> preferencesParameter = myApplication.getPreferencesParameter();
            for (int i = 0; i < igbeanList.size(); i++) {
                if(igbeanList.get(i).getType()==1){
                    IntergralBean intergralBean = igbeanList.get(i);
                    if (Integer.parseInt(SPUtil.get(this, Canstance.SharedPreferencesName, preferencesParameter.get(intergralBean.getModuleid()), 0).toString()) == 0) {
                        SPUtil.put(this, Canstance.SharedPreferencesName, preferencesParameter.get(intergralBean.getModuleid()), 1);
                    }
                }
            }
        }
//        if(igbeanList!=null && igbeanList.size()!=0){
//            Map<Integer, String> integerStringMap = myApplication.getruleNametoId();
//            integerStringMap.get(1);
//            //type==1金币收入 0 兑换
//            for (int i = 0; i < igbeanList.size(); i++) {
//                if(igbeanList.get(i).getType()==1){
//                    IntergralBean intergralBean = igbeanList.get(i);
//                    int getgold = igbeanList.get(i).getGetgold();
//                    sumScore += getgold;
//                    //处理弹窗
////                   setPopDialog(integerStringMap, intergralBean, getgold);
//                }else if (igbeanList.get(i).getType()==0 && (igbeanList.get(i).getModuleid()!=Canstance.EXCHANGECLOCK)){
//                    int getgold = igbeanList.get(i).getGetgold();
//                    if(sumScore>=getgold){
//                        sumScore= sumScore-getgold;
//                    }else {
//                        sumScore += -getgold;
//                    }
//                }
//            }
//            //if(sqtotalExp==0){
//                total_score.setText(sumScore+"");
//            //}
//            myApplication.setTotalExp(sumScore);
//        }

    }

    private void setPopDialog(Map<Integer, String> integerStringMap, IntergralBean intergralBean, int getgold) {
        if(intergralBean.getModuleid()==1){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isSUNSTEP_China);
        }
        if(intergralBean.getModuleid()==2){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isRUNKILOMETRE_China);
        }
        if(intergralBean.getModuleid()==3){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isMAKEFRIENDS);
        }
        if(intergralBean.getModuleid()==4){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isPHOTOLEARNREAD);
        }
        if(intergralBean.getModuleid()==5){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isPHOTOLEARNPICTURE);
        }
        if(intergralBean.getModuleid()==6){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isUSELITTLELOVE);
        }
        if(intergralBean.getModuleid()==7){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isRELEASEWECHAT);
        }
        if(intergralBean.getModuleid()==8){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isLIKESWECHAT);
        }
//        if(intergralBean.getModuleid()==9){
//            judgePop(integerStringMap, intergralBean, getgold, Canstance.isCOMMENTWECHAT);
//        }
        if(intergralBean.getModuleid()==10){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isOTAUPDATE);
        }
        if(intergralBean.getModuleid()==11){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isENGLISHSTUDY);
        }
        if(intergralBean.getModuleid()==12){
            judgePop(integerStringMap, intergralBean, getgold, Canstance.isENGLISHPASS);
        }
//        if(intergralBean.getModuleid()==13){
//            judgePop(integerStringMap, intergralBean, getgold, Canstance.isBUSCARD);
//        }


    }

    private void judgePop(Map<Integer, String> integerStringMap, IntergralBean intergralBean, int getgold, String isSUNSTEP_china) {
        if (Integer.parseInt(SPUtil.get(this, Canstance.SharedPreferencesName, isSUNSTEP_china, 0).toString()) == 0) {
            SPUtil.put(this, Canstance.SharedPreferencesName, isSUNSTEP_china, 1);
            final TaskFinshhintDialog taskFinshhintDialog = new TaskFinshhintDialog(this, integerStringMap.get(intergralBean.getModuleid()), getgold + "");
            if(taskFinshhintDialog!=null){
                taskFinshhintDialog.show();
            }
            final Timer t = new Timer();
            new Timer().schedule(new TimerTask() {
                public void run() {
                    taskFinshhintDialog.dismiss();
                    t.cancel();
                }
            }, 5000);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gold_payments:
                startActivity(new Intent(MainActivity.this, GoldRecordActivity.class));
                break;
            case R.id.open_bag:
                if (isClickLuckyBag) {
                    isClickLuckyBag = false;
                    SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName, Canstance.ISFIRSTENTERDAILY, 1);
                    if(openfinalbox==1){
                        SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName, Canstance.isopenFinalBox, 0);
                        SPUtil.put(MainActivity.this, Canstance.SharedPreferencesName,Canstance.isoBoxflag,1);
                        randomExp(35,30,25,20,Canstance.TYPE_OPENBOX_FINAL);
                    }else {
                        //普通宝箱
                        randomExp(20,15,10,5,Canstance.TYPE_OPENBOX_NORMAL);
                        SPUtil.put(this, Canstance.SharedPreferencesName, Canstance.isOPENBOX_China, 1);//设置开宝箱状态
                    }
                } else {
                    showToast(getString(R.string.lucky_bag_exchange_full_toast));
                }
                break;
        }
    }

    private int randomExp(int down1,int down2,int down3,int down4,int flag) {
        Log.i(TAG,"randomExp flag " + flag);
        Random mRandom = new Random();
        int number = mRandom.nextInt(100);
        if (number >= 0 && number < 5) {
            //20 5%  ,15 10% 10 25% 5 %60
            showLuckyBoxDiolog(down1);
            insertUpataProvide(down1,flag);
        } else if (number >= 5 && number < 15) {
            insertUpataProvide(down2,flag);
            refreshMain();
        } else if (number >= 15 && number < 40) {
            insertUpataProvide(down3,flag);
            refreshMain();
        } else if (number >= 40 && number < 100) {
            insertUpataProvide(down4,flag);
            refreshMain();
        }
        //adapter.notifyDataSetChanged();
        return 0;
    }

    private void showLuckyBoxDiolog(int gold){
        LuckyBoxDiolog diolog = new LuckyBoxDiolog(this);
        diolog.setDialogListener(new LuckyBoxDiolog.DialogListener() {
            @Override
            public void reFresh() {
                initdata();
            }

        });
        diolog.show();
        diolog.setopengold(gold+"");
        //延迟1秒刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gifview.setVisibility(View.GONE);
                Iv_open.setVisibility(View.VISIBLE);
                initTotalExp();
            }
        }, 1000);
    }
    private void refreshMain(){
        gifview.setVisibility(View.GONE);
        Iv_open.setVisibility(View.VISIBLE);
        initdata();
    }
	
	 /**
     * 监听总积分变化
     */
    private void ListenTotalExpChange() {
        getContentResolver().registerContentObserver(Settings.System.getUriFor("save_Total_integral"), false, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
				Log.d("duanjinqian","myApplication.getTotalExp()"+myApplication.getTotalExp());
                initTotalExp();
            }
        });
    }
    /**
     * 获取到开宝箱金币传入数据库
     */
    public void insertUpataProvide(int getgold,int flag){
        Log.i(TAG,"insertUpataProvide getgold "+getgold+" flag " + flag);
//        sumScore= sumScore+getgold;
//        myApplication.setTotalExp(myApplication.getTotalExp()+getgold);
       // total_score.setText(sumScore+"");
        Uri uri_user = Uri.parse("content://"+Canstance.AUTOHORITY+"/intergral");
        //获取当前时间
        String currentTime = DateFormatUtils.getNowDateShort();
        // 插入表中数据
        ContentValues values = new ContentValues();
        if(flag == Canstance.TYPE_OPENBOX_FINAL){
            values.put("moduleid", Canstance.LASTBOX);//终极宝箱存储起来
        }else if(flag == Canstance.TYPE_OPENBOX_NORMAL){
            values.put("moduleid", Canstance.OPENBOX);//开宝箱
        }
        values.put("type",1);//金币收入
        values.put("getgold",getgold);
        values.put("timestamp",currentTime);
        values.put("flag",1); //1表示未上传
        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);
        updateTotalExp(getgold);
    }

    private void updateTotalExp(int getgold){
        Log.i("updateTotalExp","updateTotalExp getgold is " + getgold);
        int mTotalExp = myApplication.getTotalExp();
        mTotalExp += getgold;
        myApplication.setTotalExp(mTotalExp);
    }

    private void initOnclik() {
        gold_payments.setOnClickListener(this);
        gifview.setOnClickListener(this);
    }

    private void initview() {
        sqtotalExp = myApplication.getTotalExp();
        Log.i(TAG, "initview sqtotalExp = " + sqtotalExp);
        mylistview = (ListView)findViewById(R.id.list_view);
        gold_payments = (LinearLayout)findViewById(R.id.gold_payments);
        gifview = (GifTempletView)findViewById(R.id.open_bag);
       Iv_open = (ImageView)findViewById(R.id.iv_open);
        total_score = (TextView)findViewById(R.id.total_score);
        total_score.setText(sqtotalExp+"");
        if(isFirst){
            isClickLuckyBag = true;
        }else {
            isClickLuckyBag = false;
            gifview.setVisibility(View.GONE);
            Iv_open.setVisibility(View.VISIBLE);
        }
    }

    private void setTslistData() {
        tsList.clear();
        //初始化任务列表数据
        for (int i=0;i<arrs.length;i++){
            tsBean = new TaskStateBean(arrs[i]);
            tsList.add(tsBean);
        }
    }

    private void initdata() {
        List<IntergralBean> igbeanList = myApplication.getTodayIgbeanList();
        if(igbeanList!=null && igbeanList.size()!=0) {
            for (int i = 0; i < igbeanList.size(); i++) {//ÍâÑ­»·ÊÇÑ­»·µÄŽÎÊý
                if (igbeanList.get(i).getType() == 0) {
                    igbeanList.remove(i);
                    i--;
                }
            }
        }
        setTslistData();
        if(igbeanList !=null && igbeanList.size()!=0){
            List<TaskStateBean> taskStateBeans = settsList(igbeanList);
            List<TaskStateBean> taskStateBeans1 = orderList(taskStateBeans);
            adapter = new TaskstateAdapter(this,taskStateBeans1);
            setListViewHeight(mylistview,adapter);
            mylistview.setAdapter(adapter);
        }else {
            adapter = new TaskstateAdapter(this,tsList);
            setListViewHeight(mylistview,adapter);
            mylistview.setAdapter(adapter);
        }
    }
    /**
     * 设置listview高度的方法
     * @param listView
     */
    public void setListViewHeight(ListView listView,TaskstateAdapter adapter) {
        //获取ListView对应的Adapter
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {//listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, listView);   //获得每个子item的视图
            listItem.measure(0, 0);   //先判断写入的widthMeasureSpec和heightMeasureSpec是否和当前的值相等，如果不等，重新调用onMeasure()，计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();   //累加不解释，统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));   //加上每个item之间的距离，listView.getDividerHeight()获取子项间分隔符占用的高度
        listView.setLayoutParams(params);//params.height最后得到整个ListView完整显示需要的高度
    }
    //将List按照时间倒序排列 //取出数据中的时间戳，由String转换成Date
    private  List<TaskStateBean> orderList(List<TaskStateBean> taskStateBeans){
        Date d1;
        Date d2;
        TaskStateBean igBean;
        //做一个冒泡排序，大的在数组的前列
        for(int i=0; i<taskStateBeans.size()-1; i++) {
            for (int j = i + 1; j < taskStateBeans.size(); j++) {
                int type = taskStateBeans.get(i).getType();
                if (type==1) {//如果队前日期靠前，调换顺序
                    igBean = taskStateBeans.get(i);
                    tsList.set(i, taskStateBeans.get(j));
                    tsList.set(j, igBean);
                }
            }
        }
        return tsList;
    }
    private List<TaskStateBean> settsList(List<IntergralBean> igbeanList) {
        for (int i = 0; i < igbeanList.size(); i++) {
            IntergralBean intergralBean = igbeanList.get(i);
            Map<Integer, String> integerStringMap = new MyApplication().getruleNametoId();
            TaskStateBean taskStateBean = new TaskStateBean(integerStringMap.get(intergralBean.getModuleid()), intergralBean.getModuleid()+"",intergralBean.getType(),
                    intergralBean.getGetgold()+"", intergralBean.getTimestamp(), intergralBean.getFlag());
            tsList.set(intergralBean.getModuleid(), taskStateBean);
        }
        return tsList;
    }
}
