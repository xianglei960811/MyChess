package com.bysj.xl.chess.mychess.until;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.bysj.xl.chess.mychess.R;

/**
 * author:向磊
 * date:2019/2/26
 * Describe:自定义window弹窗，自底部
 */
public class PopupWindowUntil extends PopupWindow implements View.OnClickListener {
    private OnPopWindowClickListener listener;
    private View mMenuView;
    private Activity activity;

    public PopupWindowUntil(Activity activity, OnPopWindowClickListener listener) {
        this.activity =activity;
        initView(activity, listener);
    }

    public void show() {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = activity.getWindow().getDecorView().getHeight();
        this.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }

    private void initView(Activity activity, OnPopWindowClickListener listener) {
        this.listener = listener;
        initViewSeting(activity);
        this.setContentView(mMenuView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.smssdk_DialogStyle);
        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(colorDrawable);
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int height = mMenuView.findViewById(R.id.pop_LL).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    private void initViewSeting(Activity activity) {
        Button bt_make, bt_choose, bt_cancle;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_window, null);
        bt_make = (Button) mMenuView.findViewById(R.id.pop_bt_make_picture);
        bt_choose = (Button) mMenuView.findViewById(R.id.pop_bt_choose_pictue);
        bt_cancle = (Button) mMenuView.findViewById(R.id.pop_bt_back);

        bt_cancle.setOnClickListener(this);
        bt_choose.setOnClickListener(this);
        bt_make.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        listener.onPopWindowClickListener(view);
        dismiss();
    }

    public interface OnPopWindowClickListener {
        void onPopWindowClickListener(View view);
    }
}
