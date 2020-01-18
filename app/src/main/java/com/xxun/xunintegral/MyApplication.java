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

public class MyApplication extends Application {
    public int mTotalExp = 0;
    private static Map<Integer, Integer> resultMap;
    private static Map<Integer, String> rulenameMap;
    private static Map<Integer, String> parameterMap;
    private static MyApplication context;
    private List<IntergralBean> igbeanList;
    private IntergralBean igbean;
    String arrs[] = {"开宝箱     ","计步达标","跑一公里","刷公交     ","添加好友","拍照识字","拍照识图",
            "使用小爱","发朋友圈","赞朋友圈","评朋友圈","英语学习","英语测试","升级固件","终极宝箱"};
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public  List<IntergralBean>  getIgbeanList() {
        Uri uri_user = Uri.parse("content://" + Canstance.AUTOHORITY + "/intergral");
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = context.getContentResolver().query(uri_user, new String[]{"_id", "moduleid", "actions", "type", "getgold", "timestamp", "flag"}, null, null, null);
        if (cursor != null) {
            igbeanList = new ArrayList<>();
            while (cursor.moveToNext()) {
                String string = cursor.getString(0);//自增字段
                int moduleid = cursor.getInt(1);
                int actions = cursor.getInt(2);
                int type = cursor.getInt(3);
                int getgold = cursor.getInt(4);
                String finishdata = cursor.getString(5);
                int flag = cursor.getInt(6);
                igbean = new IntergralBean("",moduleid, actions, type, getgold, finishdata, flag);
                igbeanList.add(igbean);
            }

            cursor.close();
        }
        return  igbeanList;
    }
    //最终的参数封装
    public  Map<Integer, Integer> getScoreRule() {
        resultMap = new HashMap<>();
        resultMap.put(Canstance.SUNSTEP, 5);
        resultMap.put(Canstance.RUNKILOMETRE,5);
        resultMap.put(Canstance.BUSCARD,10);
        resultMap.put(Canstance.MAKEFRIENDS,10);
        resultMap.put(Canstance.PHOTOLEARNREAD,5);
        resultMap.put(Canstance.PHOTOLEARNPICTURE,5);
        resultMap.put(Canstance.USELITTLELOVE,5);
        resultMap.put(Canstance.RELEASEWECHAT,5);
        resultMap.put(Canstance.LIKESWECHAT,5);
        resultMap.put(Canstance.COMMENTWECHAT,5);
        resultMap.put(Canstance.ENGLISHSTUDY,5);
        resultMap.put(Canstance.ENGLISHPASS,5);
        resultMap.put(Canstance.OTAUPDATE,5);
        return resultMap;
    }
    public Map<Integer, String> getPreferencesParameter() {
        parameterMap = new HashMap<>();
        parameterMap.put(0,Canstance.isOPENBOX_China);
        parameterMap.put(1,Canstance.isSUNSTEP_China);
        parameterMap.put(2,Canstance.isRUNKILOMETRE_China);
        parameterMap.put(3,Canstance.isBUSCARD);
        parameterMap.put(4,Canstance.isMAKEFRIENDS);
        parameterMap.put(5,Canstance.isPHOTOLEARNREAD);
        parameterMap.put(6,Canstance.isPHOTOLEARNPICTURE);
        parameterMap.put(7,Canstance.isUSELITTLELOVE);
        parameterMap.put(8,Canstance.isRELEASEWECHAT);
        parameterMap.put(9,Canstance.isLIKESWECHAT);
        parameterMap.put(10,Canstance.isCOMMENTWECHAT);
        parameterMap.put(11,Canstance.isENGLISHSTUDY);
        parameterMap.put(12,Canstance.isENGLISHPASS);
        parameterMap.put(13,Canstance.isOTAUPDATE);
        return parameterMap;
    }


    //最终的参数封装
    public  Map<Integer, String> getruleNametoId() {
        rulenameMap = new HashMap<>();
        rulenameMap.put(0,Canstance.OPENBOX_China);
        rulenameMap.put(1,Canstance.SUNSTEP_China);
        rulenameMap.put(2,Canstance.RUNKILOMETRE_China);
        rulenameMap.put(3,Canstance.BUSCARD_China);
        rulenameMap.put(4,Canstance.MAKEFRIENDS_China);
        rulenameMap.put(5,Canstance.PHOTOLEARNREAD_China);
        rulenameMap.put(6,Canstance.PHOTOLEARNPICTURE_China);
        rulenameMap.put(7,Canstance.USELITTLELOVE_China);
        rulenameMap.put(8,Canstance.RELEASEWECHAT_China);
        rulenameMap.put(9,Canstance.LIKESWECHAT_China);
        rulenameMap.put(10,Canstance.COMMENTWECHAT_China);
        rulenameMap.put(11,Canstance.ENGLISHSTUDY_China);
        rulenameMap.put(12,Canstance.ENGLISHPASS_China);
        rulenameMap.put(13,Canstance.OTAUPDATE_China);
        rulenameMap.put(14,Canstance.LASTBOX_China);
        return rulenameMap;
    }
    public void setTotalExp(int exp) {
        LogUtil.i("mTotalExp ++++++++++++" + exp);
        SPUtil.put(context, Canstance.SharedPreferencesName, "save_Total_integral", exp);
      }

    public int getTotalExp() {
        mTotalExp = (int) SPUtil.get(context,Canstance.SharedPreferencesName, Canstance.SAVE_TOTAL_INTEGRAL, 0); //总经验
        return this.mTotalExp;
    }
}
