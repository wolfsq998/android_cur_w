package com.example.worker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.TypedArray;
import android.graphics.Point;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/18.
 */
public class AppDataManager {
    public static AppDataManager instance = null;

    private File
            filesPath;

    private String
            city_name_now,
            city_id_now,
            user =null,
            sellWinTitle,
            appIp,
            deviceId,
            GUID,
            orderID;
    private float
            totalPrice = 0;
    private int
            sellPriceArrayId,
            sellRollAdsArrayId,
            sellIconArrayId,
            fragment_num=0,
            appPort,
            order_count,
            errcd_info,
            sellType,
            inletId,
            city_area_now,
            cardName;

    private Point
            mPoint;

    private boolean
            outSocket=false;

    private int[]
            buy_item_arr;
    private String []
            submitOrderItem;

    private ProgressDialog dialog;

    private JSONObject msg = new JSONObject();

    private ArrayList<CurtainOrderBase> COB;

    public static AppDataManager GetInstance(){
        if(instance == null)
        {
            instance = new AppDataManager();
        }
        return instance;
    }


    public void setCOB(ArrayList<CurtainOrderBase> cob){COB=cob;}
    public ArrayList<CurtainOrderBase> getCOB(){return COB;}
//用户唯一识别码
    public void setGUID(String s){GUID=s;}
    public String getGUID(){return GUID;}
//当前准备付账的账单号
    public void setOrderID(String s){
        orderID=s;
    }
    public String getOrderID(){return orderID;}
//账单信息
    public void setOrderBase(JSONObject jb){
        msg = jb;
    }
    public JSONObject getOrderBase(){
        return msg;
    }

//界面跳转ID
    public void setInletId(int i){inletId =i;}
    public int getInletId(){return inletId;}
//用户资源路径
    public void setFilesPath(File f){filesPath =f;}
    public File getFilesPath(){return filesPath;}
//当前设备屏幕大小
    public void set_mPoint(Point p){mPoint=p;}
    public Point get_mPoint(){return mPoint;}
//当前机器码
    public void setDeviceId(String di){deviceId=di;}
    public String getDeviceId(){return deviceId;}
//当前服务器握手地址
    public void setAppIp(String ip){appIp=ip;}
    public String getAppIp(){return appIp;}
//当前服务器握手端口
    public void  setAppPort(int port){appPort=port;}
    public int getAppPort(){return appPort;}
//当前服务器返回的errcd代码信息
    public void  setErrcdInfo(int e){errcd_info=e;}
    public int getErrcdInfo(){return errcd_info;}
//当前城市
    public void setCity_name_now(String s){city_name_now = s;}
    public String getCity_name_now(){return city_name_now;}
    //当前城市区号
    public void setCity_id_now(String s){city_id_now=s;}
    public String getCity_id_now(){return city_id_now;}
    //当前城市分区XML文件名
    public void setCity_area_now(int i){city_area_now =i;}
    public int getCity_area_now(){return city_area_now;}
//出售界面标题
    public void setSellWinTitle(String s){sellWinTitle=s;}
    public String getSellWinTitle(){return sellWinTitle;}
//出售物品类别
    public void setSellType(int i){sellType =i;}
    public int getSellType(){return sellType;}
//出售商品名称
    public void setCardName(int i){cardName =i;}
    public int getCardName(){return cardName;}
// 出售商品每个品种的数量
    public void setBuy_item_arr(int[] i){buy_item_arr = i;}
    public int[] getBuy_item_arr(){return buy_item_arr;}
//出售物品总价
    public void setTotalPrice(float f){totalPrice = f;}
    public float getTotalPrice(){return totalPrice;}
//出售物品单价组ID
    public void setSellPriceArrayId(int i){sellPriceArrayId = i;}
    public int getSellPriceArrayId(){return sellPriceArrayId;}
//出售物品图标组ID
    public void setSellIconArrayId(int i){sellIconArrayId = i;}
    public int getSellIconArrayId(){return sellIconArrayId;}
//出售界面滚动广告资源组ID
    public void setSellRollAdsArrayId(int i){sellRollAdsArrayId=i;}
    public int getSellRollAdsArrayId(){return sellRollAdsArrayId;}
//用户名
    public void setUser(String s){user=s;}
    public String getUser(){return user;}
//主页动态制定启动碎片
    public void setFragment_num(int i){fragment_num = i;}
    public int getFragment_num(){return fragment_num;}
//退出指令
    public void setOutSocket(boolean b){outSocket = b;}
    public boolean getOutSocket(){return outSocket;}
//账单总量
    public void setOrderCount(int i){order_count =i;}
    public int getOrderCount(){return order_count;}


//读取数组ID的方法
    public int[] getReadArrayId(TypedArray Array){
        int len = Array.length();
        final int[] arrayId = new int[len];
        for (int i=0;i<len;i++){
            arrayId[i]=Array.getResourceId(i,0);
        }
        Array.recycle();
        return arrayId;
    }
   public void showDialog(String message,Activity activity) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(activity);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    public void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }
}

