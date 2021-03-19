package com.xxun.dialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xxun.xunintegral.R;

import android.content.Intent;
import android.util.Log;

public class LuckyBoxDiolog extends Dialog {
    private TextView oepnBoxgold;
    private ImageView iv_cancel,iv_share;
    private Context mContext;
    private DialogListener dialogListener=null;
    private int mGold;

    private BroadcastReceiver shareReceiver;

    public static final String SHARE_SUCCESS = "xiaoxun.friends.publish.byjifen.success";
    public static final String SHARE_FAILED = "xiaoxun.friends.publish.byjifen.failed";

    private static final String TAG = "LuckyBoxDiolog";

    public interface DialogListener {
   //获得图片地址的回调函数的接口函数
     public void reFresh();
 }
    public LuckyBoxDiolog(Context context) {
        super(context,R.style.MyDialogStyle);
        this.mContext = context;
    }

    public LuckyBoxDiolog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_box);
        initview();
        initReceiver();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialogListener.reFresh();
        mContext.unregisterReceiver(shareReceiver);
        Log.i("dismiss", "duanjinqian dismiss ");
    }

    private void initReceiver(){
        shareReceiver = new ShareReceiver();
        IntentFilter sharefilter = new IntentFilter();
        sharefilter.addAction(SHARE_SUCCESS);
        sharefilter.addAction(SHARE_FAILED);
        mContext.registerReceiver(shareReceiver, sharefilter);
    }

    class ShareReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "intent.getAction() = : " + intent.getAction());
            if (SHARE_SUCCESS.equals(intent.getAction())) {
                dismiss();
                dialogListener.reFresh();
            }
        }
    }

    private void initview() {
        oepnBoxgold =(TextView)findViewById(R.id.tv_openbox_gold);
        iv_cancel = (ImageView)findViewById(R.id.iv_cancel);
        iv_share = (ImageView)findViewById(R.id.iv_share);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                dialogListener.reFresh();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				Intent intent = new Intent("xiaoxun.friends.publish.byjifen");
                intent.setClassName("com.xxun.watch.xunfriends", "com.xxun.watch.xunfriends.activity.PhotoActivity");
                intent.putExtra("gold", mGold);
                mContext.startActivity(intent);
                //Toast.makeText(mContext,"分享到朋友圈",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setopengold(String openboxgold){
        oepnBoxgold.setText("+"+openboxgold+"金币");
        mGold = Integer.parseInt(openboxgold);
    }


    /**********通过这个方法，将回调函数的地址传入到MyDialog中*********/
    public void setDialogListener(DialogListener listener){
        this.dialogListener=listener;
    }


}
