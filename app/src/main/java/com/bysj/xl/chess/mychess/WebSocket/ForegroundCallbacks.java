package com.bysj.xl.chess.mychess.WebSocket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * author:向磊
 * date:2018/12/20
 * Describe:监听app前后台切换
 */
public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    public static final long CHECK_DELAY = 600;
    public static final String TAG = ForegroundCallbacks.class.getSimpleName();
    private static ForegroundCallbacks INSTANCE = null;
    private Boolean foreground = false, paused = true;
    private Handler handler = new Handler();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Runnable check;

    public static ForegroundCallbacks init(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ForegroundCallbacks();
            application.registerActivityLifecycleCallbacks(INSTANCE);
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get(Application application) {
        if (INSTANCE == null) {
            init(application);
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get(Context context) {
        if (INSTANCE == null) {
            Context appCtx = context.getApplicationContext();
            if (appCtx instanceof Application) {
                init((Application) appCtx);
            }
            throw new IllegalStateException(
                    "Foreground is not initialised and" +
                            "cannot obtain the Application object"
            );
        }
        return INSTANCE;
    }

    public static ForegroundCallbacks get() {
        return INSTANCE;
    }

    public boolean isForeground() {
        return foreground;
    }

    public boolean isBackground() {
        return !foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        paused = false;
        boolean WasBackground = !foreground;
        foreground = true;
        if (check != null) {
            handler.removeCallbacks(check);
        }
        if (WasBackground) {
            for (Listener listener1 : listeners) {
                try {
                    listener1.onBecameForeground();
                } catch (Exception e) {

                }
            }
        } else {

        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        paused = true;
        if (check != null) {
            handler.removeCallbacks(check);
        }
        handler.postDelayed(check = new Runnable() {
            @Override
            public void run() {
                if (foreground && paused) {
                    foreground = false;
                    for (Listener listener1:listeners){
                        try {
                            listener1.onBecameBackground();
                        }catch (Exception e){

                        }
                    }
                }else {

                }
            }
        },CHECK_DELAY );
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public interface Listener {
        public void onBecameForeground();

        public void onBecameBackground();
    }
}
