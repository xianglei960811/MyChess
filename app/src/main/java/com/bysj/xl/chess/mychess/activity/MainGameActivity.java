package com.bysj.xl.chess.mychess.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.until.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainGameActivity extends BaseActivity {
    @BindView(R.id.game_main_ima_head)
    ImageView gameMainImaHead;
    @BindView(R.id.game_main_ima_set)
    ImageView gameMainImaSet;
    @BindView(R.id.game_main_tv_name)
    TextView gameMainTvName;
    @BindView(R.id.game_main_bt_PVP)
    Button gameMainBtPvp;
    @BindView(R.id.game_main_bt_PVC)
    Button gameMainBtPvc;
    @BindView(R.id.game_main_bt_myFriends)
    Button gameMainBtMyfriends;
    @BindView(R.id.game_main_bt_rule)
    Button gameMainBtRuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        String user_headImags = (String) SharedPreferencesUtils.getParam(this, C.USER_HEAD_NAME, C.USER_HEAD);
        String user_name = (String) SharedPreferencesUtils.getParam(this, C.USER_NAME_NAME, C.USER_NAME);
        gameMainTvName.setText(user_name);
        if (user_headImags == null || user_headImags.isEmpty()) {
            myToast.showToastMain("还没上传头像呢");
        } else {
            byte[] bytes_headIma = Base64.decode(user_headImags, Base64.DEFAULT);
            loadHeadIma(bytes_headIma);
        }

    }


    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main_game);
    }

    @OnClick(R.id.game_main_ima_head)
    public void onImaHeadClick() {
        toActivity(UsersActivity.class);
    }

    @OnClick(R.id.game_main_ima_set)
    public void onImaSetClick() {
    }

    @OnClick(R.id.game_main_bt_PVP)
    public void onBtPvpClick() {
    }

    @OnClick(R.id.game_main_bt_PVC)
    public void onBtPvcClick() {
    }

    @OnClick(R.id.game_main_bt_myFriends)
    public void onBtMyfriendsClick() {
    }

    @OnClick(R.id.game_main_bt_rule)
    public void onBtRulerClick() {
    }

    /**
     * 加载头像
     *
     * @param bytes_headIma
     */
    private void loadHeadIma(byte[] bytes_headIma) {
        Log.d(TAG, "loadHeadIma: ----------------------->");
        Drawable drawable_place = ContextCompat.getDrawable(this, R.drawable.ic_waiting);
        Drawable drawable_error = ContextCompat.getDrawable(this, R.drawable.ic_error);
        Glide.with(this)
                .load(bytes_headIma)
                .placeholder(drawable_place)
                .listener(new RequestListener<byte[], GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, byte[] model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Log.e(TAG, "onException: 加载失败：======================》" + e.getMessage());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, byte[] model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Log.d(TAG, "onResourceReady: ----------------------->加载成功");
                        return false;
                    }
                })
                .error(drawable_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(gameMainImaHead);

    }
}
