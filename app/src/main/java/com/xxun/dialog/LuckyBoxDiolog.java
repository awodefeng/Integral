package com.xxun.dialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xxun.xunintegral.R;


public class LuckyBoxDiolog extends Dialog {
    private TextView oepnBoxgold;
    private ImageView iv_cancel,iv_share;
    private Context mContext;
    private DialogListener dialogListener=null;

    public interface DialogListener {
   //获得图片地址的回调函数的接口函数
     public void getImageAddr();
 }
    public LuckyBoxDiolog(@NonNull Context context) {
        super(context,R.style.MyDialogStyle);
        this.mContext = context;
    }

    public LuckyBoxDiolog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_open_box);
        initview();
    }

    private void initview() {
        oepnBoxgold =findViewById(R.id.tv_openbox_gold);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_share = findViewById(R.id.iv_share);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                dialogListener.getImageAddr();
            }
        });
        iv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"分享到朋友圈",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setopengold(String openboxgold){
        oepnBoxgold.setText("+"+openboxgold+"金币");
    }


    /**********通过这个方法，将回调函数的地址传入到MyDialog中*********/
    public void setDialogListener(DialogListener listener){
        this.dialogListener=listener;
    }


}
