package com.xxun.xunintegral;
import android.app.Application;
import android.database.Cursor;
import android.net.Uri;
import com.xxun.bean.IntergralBean;
import com.xxun.util.LogUtil;
import com.xxun.util.SPUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.xxun.util.SPUtil;
import com.xxun.util.DateFormatUtils;
import android.provider.Settings;

public class MyApplication extends Application {
    public int mTotalExp = 0;
    private static HashMap<Integer, Integer> resultMap;
    private static HashMap<Integer, String> rulenameMap;
    private static HashMap<Integer, String> parameterMap;
    private static MyApplication context;
    private ArrayList<IntergralBean> igbeanList;
    private IntergralBean igbean;
    String arrs[] = {"开宝箱     ","计步达标","跑一公里",/*"刷公交     ",*/"添加好友","拍照识字","拍照识图",
            "使用小爱","发朋友圈","赞朋友圈","终极宝箱"/*"评朋友圈"*/,"升级固件","英语学习","英语测试"/*,"终极宝箱"*/};
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public  List<IntergralBean>  getTodayIgbeanList() {
        Uri uri_user = Uri.parse("content://" + Canstance.AUTOHORITY + "/intergral");
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = context.getContentResolver().query(uri_user, new String[]{"_id", "moduleid", "type", "getgold", "timestamp", "flag","exchangename"}, null, null, null);
        if (cursor != null) {
            igbeanList = new ArrayList<IntergralBean>();
            while (cursor.moveToNext()) {
                String string = cursor.getString(0);//自增字段
                int moduleid = cursor.getInt(1);
                int type = cursor.getInt(2);
                int getgold = cursor.getInt(3);
                String finishdata = cursor.getString(4);
                int flag = cursor.getInt(5);
                String exchangeName = cursor.getString(6);
                if(DateFormatUtils.isToday(finishdata)){
                    igbean = new IntergralBean("",moduleid, type, getgold, finishdata, flag, exchangeName);
                    igbeanList.add(igbean);
                }
            }
            cursor.close();
        }
        return  igbeanList;
    }

    public  List<IntergralBean>  getIgbeanList() {
        Uri uri_user = Uri.parse("content://" + Canstance.AUTOHORITY + "/intergral");
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = context.getContentResolver().query(uri_user, new String[]{"_id", "moduleid", "type", "getgold", "timestamp", "flag","exchangename"}, null, null, null);
        if (cursor != null) {
            igbeanList = new ArrayList<IntergralBean>();
            while (cursor.moveToNext()) {
                String string = cursor.getString(0);//自增字段
                int moduleid = cursor.getInt(1);
                int type = cursor.getInt(2);
                int getgold = cursor.getInt(3);
                String finishdata = cursor.getString(4);
                int flag = cursor.getInt(5);
                String exchangeName = cursor.getString(6);
                igbean = new IntergralBean("",moduleid, type, getgold, finishdata, flag, exchangeName);
                igbeanList.add(igbean);
            }
            cursor.close();
        }
        return  igbeanList;
    }

    //最终的参数封装
    public  Map<Integer, Integer> getScoreRule() {
        resultMap = new HashMap<Integer, Integer>();
        resultMap.put(Canstance.SUNSTEP, 5);
        resultMap.put(Canstance.RUNKILOMETRE,5);
        resultMap.put(Canstance.MAKEFRIENDS,10);
        resultMap.put(Canstance.PHOTOLEARNREAD,5);
        resultMap.put(Canstance.PHOTOLEARNPICTURE,5);
        resultMap.put(Canstance.USELITTLELOVE,5);
        resultMap.put(Canstance.RELEASEWECHAT,5);
        resultMap.put(Canstance.LIKESWECHAT,5);
//        resultMap.put(Canstance.COMMENTWECHAT,5);
        resultMap.put(Canstance.OTAUPDATE,5);
        resultMap.put(Canstance.ENGLISHSTUDY,5);
        resultMap.put(Canstance.ENGLISHPASS,5);
//        resultMap.put(Canstance.BUSCARD,10);
        return resultMap;
    }
    public Map<Integer, String> getPreferencesParameter() {
        parameterMap = new HashMap<Integer, String>();
        parameterMap.put(0,Canstance.isOPENBOX_China);
        parameterMap.put(1,Canstance.isSUNSTEP_China);
        parameterMap.put(2,Canstance.isRUNKILOMETRE_China);
        parameterMap.put(3,Canstance.isMAKEFRIENDS);
        parameterMap.put(4,Canstance.isPHOTOLEARNREAD);
        parameterMap.put(5,Canstance.isPHOTOLEARNPICTURE);
        parameterMap.put(6,Canstance.isUSELITTLELOVE);
        parameterMap.put(7,Canstance.isRELEASEWECHAT);
        parameterMap.put(8,Canstance.isLIKESWECHAT);
//        parameterMap.put(9,Canstance.isCOMMENTWECHAT);
        parameterMap.put(10,Canstance.isOTAUPDATE);
        parameterMap.put(11,Canstance.isENGLISHSTUDY);
        parameterMap.put(12,Canstance.isENGLISHPASS);
//        parameterMap.put(13,Canstance.isBUSCARD);
        return parameterMap;
    }

    //最终的参数封装
    public  Map<Integer, String> getruleNametoId() {
        rulenameMap = new HashMap<Integer,String>();
        rulenameMap.put(0,Canstance.OPENBOX_China);
        rulenameMap.put(1,Canstance.SUNSTEP_China);
        rulenameMap.put(2,Canstance.RUNKILOMETRE_China);
        rulenameMap.put(3,Canstance.MAKEFRIENDS_China);
        rulenameMap.put(4,Canstance.PHOTOLEARNREAD_China);
        rulenameMap.put(5,Canstance.PHOTOLEARNPICTURE_China);
        rulenameMap.put(6,Canstance.USELITTLELOVE_China);
        rulenameMap.put(7,Canstance.RELEASEWECHAT_China);
        rulenameMap.put(8,Canstance.LIKESWECHAT_China);
//        rulenameMap.put(9,Canstance.COMMENTWECHAT_China);
        rulenameMap.put(9,Canstance.LASTBOX_China);
        rulenameMap.put(10,Canstance.OTAUPDATE_China);
        rulenameMap.put(11,Canstance.ENGLISHSTUDY_China);
        rulenameMap.put(12,Canstance.ENGLISHPASS_China);
//        rulenameMap.put(13,Canstance.LASTBOX_China);
//        rulenameMap.put(14,Canstance.BUSCARD_China);
        rulenameMap.put(15,Canstance.ExchangeDial_China);
        return rulenameMap;
    }

//    public void setTotalExp(int exp) {
//        LogUtil.i("mTotalExp ++++++++++++" + exp);
//                SPUtil.put(context, Canstance.SharedPreferencesName, "save_Total_integral", exp);
//      }
//
//    public int getTotalExp() {
//        int mTotalExp = Integer.parseInt(SPUtil.get(context,Canstance.SharedPreferencesName, Canstance.SAVE_TOTAL_INTEGRAL, 0).toString()); //总经验
//        return mTotalExp;
//    }

    /**
     *  现在总积分变动都在launcher中操作，在此注意总积分不能随意set
     * @param setTotalExp
     */
    public void setTotalExp(int exp) {
        //SPUtil.put(this, "oneshaName", "save_Total_integral", totalexExp);
        Settings.System.putInt(context.getContentResolver(), Canstance.SAVE_TOTAL_INTEGRAL, exp);
        LogUtil.i("mTotalExp ++++++++++++" + exp);
    }

    public int getTotalExp() {
        int mTotalExp = Settings.System.getInt(context.getContentResolver(), Canstance.SAVE_TOTAL_INTEGRAL, 0);
        return mTotalExp;
    }

    public boolean isFirstEnterToday(){
        String lastDateTime = String.valueOf(SPUtil.get(context,Canstance.SharedPreferencesName,Canstance.SP_FIRST_DAILY_DATE_TIME, ""));
        String currentDateTime = DateFormatUtils.getNowDateToStr("yyyyMMdd");
        if (!lastDateTime.equals(currentDateTime)) {
            SPUtil.put(context,Canstance.SharedPreferencesName,Canstance.SP_FIRST_DAILY_DATE_TIME, currentDateTime);
            return true;
        }else{
            return false;
        }
    }

}
