package com.example.worker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.netease.pomelo.DataCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/4/1.
 */
public class RegWindow extends AppCompatActivity implements View.OnClickListener {

    private static final String ARG = "RegWindow";
    public static RegWindow instance =null;

    private int m_TimerNum=59;

    private static final int USERREG =0;
    private static final int GETSMS =1;//获取手机验证码
    private int m_ErrcdInfo=0;
    private int m_Inlet=-1;
    private int m_RegType;

    private EditText m_PhoneEt,m_SmsEt,m_PawEt1,m_PawEt2,m_WIEt,m_WNEt;
    private Button m_GetSmsBut,m_RegBut,m_RetBut;
    private TextView m_LabelTv,m_PasMsgTv;
    private LinearLayout m_SmsLl;
    private CheckBox m_CheckBoxXY;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == USERREG) {
                switch (m_ErrcdInfo){
                    case -1:
                        AppDataManager.GetInstance().setErrcdInfo(-1);
                        AppDataManager.GetInstance().setUser(m_PhoneEt.getText().toString());
                        finish();
                        break;
                    case 100:
                        ToolsManager.GetInstance().setMyToast(RegWindow.this, "此账号已注册，请直接登陆！", 2);
                        break;
                    default:
                        ToolsManager.GetInstance().setMyToast(RegWindow.this, "注册失败，请稍后重试！", 2);
                        break;
                }
            }
            if (msg.what == GETSMS){
                m_TimerNum--;
                m_GetSmsBut.setText(String.valueOf(m_TimerNum) + " 秒后重新获取");
                if (m_TimerNum<1){
                    m_GetSmsBut.setText("获取验证码");
                    m_GetSmsBut.setEnabled(true);
                    m_GetSmsBut.setTextColor(getResources().getColor(R.color.colorWhite));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_win);
        instance=this;

        MyClient.GetInstance().setServerAddre(getResources().getString(R.string.app_ip),Integer.parseInt(getResources().getString(R.string.app_port)));
        m_Inlet=AppDataManager.GetInstance().getInletId();

        Intent getRegType = getIntent();
        m_RegType=getRegType.getIntExtra("regType", -1);
        init();

        if (m_RegType == 0){
            m_LabelTv.setText("注册");
//            m_PasMsgTv.setText("设置登录密码");
            m_RegBut.setText("立即注册");
        }
        if (m_RegType == 1){
            m_SmsLl.setVisibility(View.GONE);
            m_LabelTv.setText("重置密码");
//            m_PasMsgTv.setText("设置新的登录密码");
            m_RegBut.setText("立即重置");
        }
    }

    private void init(){
        m_PhoneEt = (EditText)findViewById(R.id.reg_win_phone_et);//注册账号
        m_SmsEt = (EditText)findViewById(R.id.reg_win_sms_et);//绑定的商户编码
        m_PawEt1 = (EditText)findViewById(R.id.reg_win_paw_et1);//第一次输入密码
        m_PawEt2 = (EditText)findViewById(R.id.reg_win_paw_et2);//第二次输入密码
        m_WIEt = (EditText)findViewById(R.id.worker_iden_et);//工人身份证
        m_WNEt = (EditText)findViewById(R.id.worker_name_et);//工人真实姓名

        m_SmsLl = (LinearLayout)findViewById(R.id.sms_ll);

        m_LabelTv = (TextView)findViewById(R.id.reg_win_label_tv);
//        m_PasMsgTv = (TextView)findViewById(R.id.reg_win_pas_msg_tv);

//        m_GetSmsBut = (Button)findViewById(R.id.reg_win_sms_but);
//        m_GetSmsBut.setOnClickListener(this);
        m_RegBut = (Button)findViewById(R.id.reg_win_reg_but);
        m_RegBut.setOnClickListener(this);
        m_RetBut = (Button)findViewById(R.id.reg_win_return_but);
        m_RetBut.setOnClickListener(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent ev) {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            finish();
        }
        return false;
    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_win_reg_but:
                if (m_PhoneEt.getText().length()==11){
                    if (m_SmsEt.getText().length()>0){
                        if (m_WIEt.getText().length()==18){
                            if (m_WNEt.getText().length()>2){
                                if(m_PawEt1.getText().length()>5){
                                    if(m_PawEt1.getText().toString().equals(m_PawEt2.getText().toString())){
                                        MyReg();
                                    }else{
                                        ToolsManager.GetInstance().setMyToast(RegWindow.this, "两次密码不同，请重新输入！",1);
                                    }
                                }else{
                                    ToolsManager.GetInstance().setMyToast(RegWindow.this, "密码长度有误！",1);
                                }
                            }else{
                                ToolsManager.GetInstance().setMyToast(RegWindow.this, "真实姓名填写有误！", 1);
                            }
                        }else{
                            ToolsManager.GetInstance().setMyToast(RegWindow.this, "身份证号长度有误！", 1);
                        }
                    }else{
                        ToolsManager.GetInstance().setMyToast(RegWindow.this, "商户编码不能为空！", 1);
                    }
                }else{
                    ToolsManager.GetInstance().setMyToast(RegWindow.this, "您输入的注册号码长度有误！", 1);
                }

                break;
            case R.id.reg_win_return_but:
                finish();
                break;
            default:
                MyReg();
                break;
        }
    }
    /**
     * 注册
     */
    private void MyReg(){
        Log.i("@@@" + ARG, "注册！");
        final String desKey = "12345678";
        String pwd = "";
        try {
            pwd = Des.encryptDES(m_PawEt1.getText().toString(),desKey);
            Log.i("@@@：" + ARG, "加密后的密码→" + pwd);
            String pwd1 = Des.decryptDES(pwd,desKey);
            Log.i("@@@：" + ARG, "解密后的密码→" + pwd1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("@@@：" + ARG, "解密后的密码→" + e);
        }
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        JSONObject msg = new JSONObject();//一种数据传输方式
        try {
            msg.put("wname",m_PhoneEt.getText().toString());
            msg.put("num",Integer.parseInt(m_SmsEt.getText().toString()));
            msg.put("wpaw",pwd);
            msg.put("uname",m_WNEt.getText());
            msg.put("iden",m_WIEt.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("@@@：" + ARG, "注册信息→" + msg);
        MyClient.GetInstance().getClient().request("connector.workerHandler.reguser",msg, new DataCallBack() {
            @Override
            public void responseData(JSONObject msg) {
                Log.i("@@@：" + ARG, "注册返回值→" + msg);
                try {
                    m_ErrcdInfo = msg.getInt("errcd");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what =USERREG;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 重置密码
     */
    private void UpdatePassward(){
        Log.i("@@@" + ARG, "重置密码！");
        final String desKey = "12345678";
        String pwd = "";
        try {
            pwd = Des.encryptDES(m_PawEt1.getText().toString(),desKey);
            Log.i("@@@：" + ARG, "加密后的密码→" + pwd);
            String pwd1 = Des.decryptDES(pwd,desKey);
            Log.i("@@@：" + ARG, "解密后的密码→" + pwd1);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("@@@：" + ARG, "解密后的密码→" + e);
        }
        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        JSONObject msg = new JSONObject();//一种数据传输方式
        try {
            int smsnum = Integer.parseInt(m_SmsEt.getText().toString());
            msg.put("phone",m_PhoneEt.getText().toString());
            msg.put("sms",smsnum);
            msg.put("pas",pwd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("@@@：" + ARG, "重置密码→" + msg);
        MyClient.GetInstance().getClient().request("connector.loginHandler.changepas",msg, new DataCallBack() {
            @Override
            public void responseData(JSONObject msg) {
                Log.i("@@@：" + ARG, "重置密码返回值→" + msg);
                try {
                    m_ErrcdInfo = msg.getInt("errcd");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what =USERREG;
                handler.sendMessage(message);
            }
        });
    }

    private void initView(int i){
        Timer m_Timer = new Timer();
        TimerTask m_Task = new TimerTask() {
            @Override
            public void run() {
                Message message = new Message();
                message.what =GETSMS;
                handler.sendMessage(message);
            }
        };

        if (i<1) {
            m_Timer.cancel();
            m_TimerNum=59;
        }
        else
            m_Timer.schedule(m_Task, 1000, 1000);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
