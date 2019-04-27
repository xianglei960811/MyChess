package com.bysj.xl.chess.mychess.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsUserAllSUccessEvent;
import com.bysj.xl.chess.mychess.entity.FormatConversion.BitmapToBase64;
import com.bysj.xl.chess.mychess.entity.LoadImage;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.RoundImageView;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.bysj.xl.chess.mychess.until.PopupWindowUntil;
import com.bysj.xl.chess.mychess.until.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class UsersActivity extends BaseActivity implements PopupWindowUntil.OnPopWindowClickListener {
    @BindView(R.id.user_ima_head)
    RoundImageView userImaHead;
    @BindView(R.id.user_et_name)
    EditText userEtName;
    @BindView(R.id.user_et_age)
    EditText userEtAge;
    @BindView(R.id.user_et_sex)
    TextView userEtSex;
    @BindView(R.id.user_et_creatTime)
    TextView userEtCrreatTime;
    @BindView(R.id.user_tv_phone)
    TextView userTvPhone;
    @BindView(R.id.user_et_QQ)
    TextView userTvQQ;
    @BindView(R.id.user_bt_save)
    Button userBtSave;
    @BindView(R.id.user_bt_back)
    Button userBtBack;
    @BindView(R.id.user_et_grade)
    TextView userEtGrade;
    @BindView(R.id.user_bt_bind_QQ)
    Button userBtBindQQ;
    @BindView(R.id.user_bt_bind_phone)
    Button userBtBindPhone;

    private PopupWindowUntil popupWindowUntil;
    private static final int PICTURE_CHOOSE_CODE = 1;//相册
    private static final int PICTURE_MAKE_CODE = 2;//相机
    private static final int PICTURE_CROP = 3;//裁剪请求码
    private static final int PHONE_BIND_CODE = 6;//绑定手机请求吗
    private File tempFile;//调用相机返回图片文件
    private String mFile;//最后显示的图片文件

    private volatile String openId;

    private Tencent mTencent;
    private TencentUser tencentUser;
    private UserInfo userInfo;

    private String tempName;//用于一进来存储用户名称

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ===========================================>");
        init();
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.demo);
        userImaHead.setImageDrawable(drawable);

        mTencent = Tencent.createInstance("1107791795", UsersActivity.this);
        tencentUser = new TencentUser();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    protected void initView() {

    }

    private void init() {
        Log.e(TAG, "------------------------------->init: aaaaaaa");
        String name = (String) SharedPreferencesUtils.getParam(this, C.USER_NAME_NAME, C.USER_NAME);
        int age = (int) SharedPreferencesUtils.getParam(this, C.USER_AGE_NAME, C.USER_AGE_);
        String sex = (String) SharedPreferencesUtils.getParam(this, C.USER_SEX_NAME, C.USER_SEX);
        String phone = (String) SharedPreferencesUtils.getParam(this, C.USER_PHONE_NAME, C.USER_PHONE);
        String QQ_NAME = (String) SharedPreferencesUtils.getParam(this, C.USER_QQ_NAME, C.USER_QQ);
        if (name == null || name.isEmpty()) {
            userEtName.setText("");
        } else {
            userEtName.setText(name);
            this.tempName = name;
        }
        userEtAge.setText(Integer.toString(age));
        if (sex == null || sex.isEmpty()) {
            userEtSex.setText("");
        } else {
            userEtSex.setText(sex);
        }

        if (phone == null || phone.isEmpty()) {
            userTvPhone.setHint("请绑定手机号");
            userBtBindPhone.setText("绑定");
        } else {
            userTvPhone.setText(phone);
            userBtBindPhone.setText("更换");
        }
        if (QQ_NAME == null || QQ_NAME.isEmpty()) {
            userTvQQ.setHint("请绑定QQ");
            userBtBindQQ.setText("绑定");
        } else {
            userTvQQ.setText(QQ_NAME);
            userBtBindQQ.setText("更换");
        }

        String headIma = (String) SharedPreferencesUtils.getParam(this, C.USER_HEAD_NAME, C.USER_HEAD);
        String grade = (String) SharedPreferencesUtils.getParam(this, C.USER_GRADE_NAME, C.USER_GRADE);
        String creatTime = (String) SharedPreferencesUtils.getParam(this, C.USER_CREAT_TIME_NAME, C.USER_CREAT_TIME);
        if (grade == null || grade.isEmpty()) {
            userEtGrade.setText("");
        } else {
            userEtGrade.setText(grade);
        }
        if (headIma == null || headIma.isEmpty()) {
            myToast.showToastMain("请上传用户头像");
        } else {
            byte[] bytes_ima = Base64.decode(headIma, Base64.DEFAULT);
            LoadImage.getINSTANCE(this).LoadRoundImage(bytes_ima, userImaHead);
        }
        if (creatTime == null || creatTime.isEmpty()) {
            userEtCrreatTime.setText("");
        } else {
            userEtCrreatTime.setText(creatTime);
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_users);
    }


    @Override
    protected void onSaveInstanceState(Bundle data) {
        // TODO: 2019/2/27  activiy被摧毁时储存数据
        data.putString("name", userEtName.getText().toString().trim());
        data.putString("age", userEtAge.getText().toString().trim());
        SharedPreferencesUtils.setParam(this, C.USER_NAME_NAME, userEtName.getText().toString());
        SharedPreferencesUtils.setParam(this, C.USER_AGE_NAME, Integer.parseInt(userEtAge.getText().toString()));
        super.onSaveInstanceState(data);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userEtName.setText(savedInstanceState.getString("name"));
        userEtAge.setText(savedInstanceState.getString("age"));
        Log.e(TAG, "init:--------------------------------> temp");
    }

    @SuppressLint("ResourceType")
    @OnClick(R.id.user_ima_head)
    public void onHeadImagClick() {
        Log.e(TAG, "onHeadImagClick: --------------------------->");
        popupWindowUntil = new PopupWindowUntil(this, this);
        popupWindowUntil.show();
    }


    @OnClick(R.id.user_bt_bind_QQ)
    public void onBindQQClick() {
        if (userTvQQ.getText().toString().trim() == null || userTvQQ.getText().toString().trim().isEmpty()) {
            myToast.showToastMain("请输入你需要绑定的QQ后验证");
            return;
        }
        mTencent.login(this, "all", new QQCallBack());
    }

    @OnClick(R.id.user_bt_bind_phone)
    public void onBindPhoneClick() {
        Intent intent = new Intent(this, BindPhoneActivity.class);
        intent.putExtra("name", tempName);
        startActivityForResult(intent, PHONE_BIND_CODE);
    }

    @OnClick(R.id.user_bt_save)
    public void onSaveClick() {
        Request request = new Request();
        TencentUser user = new TencentUser();
//        user.setUser_headIma(BitmapToBase64.bitmapToBase64(((BitmapDrawable) userImaHead.getDrawable()).getBitmap()));
//        Bitmap bitmap =( (BitmapDrawable) userImaHead.getDrawable()).getBitmap();
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//
        user.setUser_name(userEtName.getText().toString().trim());
        if (userEtAge.getText().toString().trim() == null || userEtAge.getText().toString().trim().isEmpty()) {
            user.setUsr_age(0);
        } else {
            user.setUsr_age(Integer.parseInt(userEtAge.getText().toString().trim()));
        }
        user.setQQ(userTvQQ.getText().toString());
        user.setUsr_Sex(userEtSex.getText().toString().trim());
        user.setQQopenID(openId);
        request.setData(user);
        request.setUsr_request_type(C.USE_ALL);
        request.setMsg("Moodify the user's info");
        wsInterface.sendMsg(new Gson().toJson(request));
    }

    @OnClick(R.id.user_bt_back)
    public void onBackClick() {
        toActivityWithFinish(MainGameActivity.class);
    }


    @Override
    public void onPopWindowClickListener(View view) {
        //弹窗点击事件
        switch (view.getId()) {//拍照
            case R.id.pop_bt_make_picture:
                Log.e(TAG, "make____________________________________>");
                getPicFromCamera();
                break;
            case R.id.pop_bt_choose_pictue://从相册中选择照片
                Log.e(TAG, "choose----------------------------------->");
                getPicFromAlbm();
                break;
            case R.id.pop_bt_back://取消
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: ================================>" + requestCode + ":::::::::" + resultCode);
        switch (requestCode) {
            case PICTURE_MAKE_CODE://调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", tempFile);
                        startPhotoZoom(contentUri);
                    } else {
                        startPhotoZoom(Uri.fromFile(tempFile));
                    }
                }
                break;
            case PICTURE_CHOOSE_CODE://调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    startPhotoZoom(uri);
                }
                break;
            case PICTURE_CROP://调用裁剪后返回
                if (data != null) {
                    // 让刚才选择裁剪得到的图片显示在界面上
                    Bitmap photo = BitmapFactory.decodeFile(mFile);
                    String pictureBase64 = BitmapToBase64.bitmapToBase64(photo);
                    if (pictureBase64 == null || pictureBase64.isEmpty()) {
                        myToast.showToastMain("头像更新失败，请更换图片！");
                        Log.e(TAG, "onActivityResult: =================================>pictureBase64 is empty");
                        break;
                    }
                    SharedPreferencesUtils.setParam(this, C.USER_HEAD_NAME, pictureBase64);
                    LoadImage.getINSTANCE(this).LoadRoundImage(Base64.decode(pictureBase64, Base64.DEFAULT), userImaHead);
                } else {
                    Log.e(TAG, "onActivityResult: ========================>data数据为空");
                }
                break;
            case Constants.REQUEST_LOGIN://QQ登录回调
                Tencent.onActivityResultData(requestCode, resultCode, data, new QQCallBack());
                break;
            case PHONE_BIND_CODE://绑定手机回调
                if (resultCode == RESULT_OK) {
                    String phone = data.getStringExtra("phone");
                    Log.i(TAG, "onActivityResult: ========================>phone:" + phone);
                    userTvPhone.setText(phone);
                    SharedPreferencesUtils.setParam(this, C.USER_PHONE_NAME, phone);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 从系统相册中获取头像
     */
    private void getPicFromAlbm() {
        Intent albmIntent = new Intent(Intent.ACTION_PICK);
        albmIntent.setType("image/*");
        startActivityForResult(albmIntent, PICTURE_CHOOSE_CODE);
    }

    /**
     * 从相机中获取头像
     */
    private void getPicFromCamera() {
//用于保存拍照后生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        //跳转到调用系统相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //如果在Android7.0以上,使用FileProvider获取Uri
            cameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", tempFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {//否则使用Uri.fromFile(file)方法获取Uri
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, PICTURE_MAKE_CODE);
        } else {
            myToast.showToastMain("无法链接照相机");
        }
    }

    /**
     * 裁剪方法的具体实现
     *
     * @param contentUri
     */
    private void startPhotoZoom(Uri contentUri) {
        if (contentUri == null) {
            Log.i(TAG, "startPhotoZoom: ========================>this uri is exist.");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(contentUri, "image/*");
        //设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 100);
        intent.putExtra("outputY", 100);
        intent.putExtra("return_data", false);
        File out = new File(getPath());
        if (!out.getParentFile().exists()) {
            out.getParentFile().mkdirs();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out));
        startActivityForResult(intent, PICTURE_CROP);
    }

    /**
     * 获取裁剪后图片的地址
     *
     * @return
     */
    private String getPath() {
        if (mFile == null) {
            mFile = Environment.getExternalStorageDirectory() + "/" + "wode/" + "outtemp.png";
        }
        return mFile;
    }

    /**
     * QQ回调接口
     */
    private class QQCallBack implements IUiListener {
        @Override
        public void onComplete(Object response) {
            try {
                openId = ((JSONObject) response).getString("openid");
                String accessToken = ((JSONObject) response).getString("access_token");
                String expirs = ((JSONObject) response).getString("expires_in");
                myToast.showToastMain("QQ登录成功");
                mTencent.setOpenId(openId);
                mTencent.setAccessToken(accessToken, expirs);
                userInfo = new UserInfo(UsersActivity.this, mTencent.getQQToken());
                userInfo.getUserInfo(new QQUserInfoListener());
            } catch (JSONException e) {
                Log.e(TAG, "onComplete: ==============================>QQ返回数据解析失败" + e.getMessage());
                myToast.showToastMain("QQ登录失败，请重新登录");
            }

        }

        @Override
        public void onError(UiError uiError) {
            Log.e(TAG, "onError: ------------------------------>QQ登录错误" + uiError.errorMessage);

        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ------------------------->取消登录");
        }
    }

    /**
     * 获取qq用户信息接口
     */
    private class QQUserInfoListener implements IUiListener {
        @Override
        public void onComplete(Object userInfo) {
            Log.e(TAG, "onComplete: ========================>" + userInfo.toString());
            JSONObject user = (JSONObject) userInfo;
            try {
                String QQName = user.getString("nickname");
                SharedPreferencesUtils.setParam(UsersActivity.this, C.USER_QQ_NAME, QQName);
                userTvQQ.setText(QQName);
                userBtBindQQ.setText("更换");
            } catch (JSONException e) {
                Log.e(TAG, "onComplete: error==============================>json数据解析失败，" + e.getMessage());
                e.printStackTrace();
            }


        }

        @Override
        public void onError(UiError uiError) {
            myToast.showToastMain("QQ用户信息拉取失败");
        }

        @Override
        public void onCancel() {
            Log.e(TAG, "onCancel: ------------------------->取消拉起QQ用户信息");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void UseAllSuccessEvent(WsUserAllSUccessEvent event) {
        myToast.showToastMain("用户修改成功");
        toActivityWithFinish(MainGameActivity.class);
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: =======================>");
//        SharedPreferencesUtils.clear(this);
    }


}
