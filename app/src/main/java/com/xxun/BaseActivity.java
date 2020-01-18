package com.xxun;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

public class BaseActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void openActivity1(Class<?> pClass){
        Intent intent = new Intent();
        intent.setClass(this,pClass);
        startActivity(intent);
    }
    protected <T extends View> T fd(@IdRes int id){
        return findViewById(id);
    }
    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }
}
