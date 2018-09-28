package com.bysj.xl.chess.mychess.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.bysj.xl.chess.mychess.WebSocketClient.Service.WebSocketSerice;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static App instance;
    private List<Activity> allActivities;
    private static Context context;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context =getApplicationContext();
        instance = this;
        Intent intent = new Intent(this,WebSocketSerice.class);
        startService(intent);
    }
    public  static Context getContext(){
        return context;
    }


    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new ArrayList<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
            act.finish();
        }
    }
    /**
     * 退出app
     */
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


}