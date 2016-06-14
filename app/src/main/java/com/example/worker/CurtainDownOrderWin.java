package com.example.worker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.netease.pomelo.DataCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/7.
 */
public class CurtainDownOrderWin extends AppCompatActivity {

    public static CurtainDownOrderWin instance =null;
    private static final String ARG = "CurtainDownOrderWin";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curtain_download_order_window);
        instance=this;
        init();
    }

    private void init(){
        Button toMyInfoBut = (Button)findViewById(R.id.my_info_but);
        toMyInfoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurtainDownOrderWin.this, MyInfoWindow.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        CurtainOrderFragment testFragment = new CurtainOrderFragment();
        getFragmentManager().beginTransaction().replace(R.id.download_fragment_order_ll, testFragment).commit();
        super.onStart();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent ev) {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            finish();
        }
        return false;
    }
}
