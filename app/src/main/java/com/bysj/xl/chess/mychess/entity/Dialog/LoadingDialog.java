package com.bysj.xl.chess.mychess.entity.Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.entity.Dialog.Loading.LoadingBar;
import com.bysj.xl.chess.mychess.entity.Dialog.Loading.MyLoading;
import com.bysj.xl.chess.mychess.entity.Dialog.Loading.timeOutListner;
import com.bysj.xl.chess.mychess.entity.getScreenSize.ScreenSizeUtils;


/**
 * Author: XL
 * Date: 2018-10-22 11:03
 * Describe:加载弹窗动画
 */
public class LoadingDialog extends MyLoading {
    private LoadingBar loadingBar;
    private Context context;

    public LoadingDialog(@NonNull Context context, int timeout, com.bysj.xl.chess.mychess.entity.Dialog.Loading.timeOutListner timeOutListner) {
        super(context, timeout, timeOutListner);
        this.context = context;
    }

    public LoadingDialog(@NonNull Context context, int themeResId, int timeout, timeOutListner timeOutListner) {
        super(context, themeResId, timeout, timeOutListner);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payloading, null);
        loadingBar = view.findViewById(R.id.scan_dialog);
        this.setContentView(view);
        this.setCanceledOnTouchOutside(false);
        view.setMinimumHeight((int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.3f));
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.3f);
        lp.height = (int) (ScreenSizeUtils.getInstance(context).getScreenWidth() * 0.3f);
        lp.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(lp);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        loadingBar = null;
    }

    public LoadingBar getLoadingBar() {
        return loadingBar;
    }
}
