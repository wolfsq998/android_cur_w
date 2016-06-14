package com.example.worker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/5/22.
 */
public class MyInfoWindow extends AppCompatActivity implements View.OnClickListener{

    public static MyInfoWindow instance =null;
    private static final String ARG = "MyInfoWindow";
    private static final int UPDATE = 0;

    private TextView m_LoginTv;
    private ImageButton m_BreakBut;
    private Button m_OutLoginBut;

    private SharedPreferences m_Preferences;
    private SharedPreferences.Editor m_Editor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_window);
        instance=this;

        m_Preferences = getSharedPreferences("worker_phone",MODE_PRIVATE);
        m_Editor = m_Preferences.edit();

        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String appVersion = info.versionName; // 版本名，versionCode同理
            TextView appVersionTv = (TextView)findViewById(R.id.about_us_app_version_tv);
            appVersionTv.setText("V\r\r"+appVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        init();
    }
    private void init(){
        int errcd = AppDataManager.GetInstance().getErrcdInfo();
        m_LoginTv = (TextView)findViewById(R.id.login_tv);
        String user = AppDataManager.GetInstance().getUser();
        if (errcd == -1){
            m_LoginTv.setText(user);
        }else{

        }

        m_OutLoginBut = (Button)findViewById(R.id.out_login_but);
        m_OutLoginBut.setOnClickListener(this);

        m_BreakBut = (ImageButton)findViewById(R.id.break_img_but);
        m_BreakBut.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.break_img_but:
                finish();
                break;
            case R.id.out_login_but:
                AppDataManager.GetInstance().setErrcdInfo(-100);
                m_Editor.putString("tel", "0");
                m_Editor.putString("pwd", "0");
                m_Editor.commit();
                m_LoginTv.setText("");
                Intent toLoginWin = new Intent(MyInfoWindow.this,LoginWindow.class);
                startActivity(toLoginWin);
                CurtainDownOrderWin.instance.finish();
                finish();
                break;
        }
    }
}
