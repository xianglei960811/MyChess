package com.bysj.xl.chess.mychess.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bysj.xl.chess.mychess.WebSocket.ForegroundCallbacks;
import com.bysj.xl.chess.mychess.WebSocket.WbManager.WsManager;

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
        context = getApplicationContext();
        instance = this;
//        startService(intent);
        initAppStatusListener();
        WsManager.getINSTANCE().init();
    }

    private void initAppStatusListener() {
        ForegroundCallbacks.init(this).addListener(new ForegroundCallbacks.Listener() {
            @Override
            public void onBecameForeground() {
                Log.i("APP", "应用回到前台，调用重连 ");
                WsManager.getINSTANCE().reConnect();
            }

            @Override
            public void onBecameBackground() {

            }
        });
    }

    public static Context getContext() {
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