package com.bysj.xl.chess.mychess.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bysj.xl.chess.mychess.Base.BaseActivity;
import com.bysj.xl.chess.mychess.Constant.C;
import com.bysj.xl.chess.mychess.R;
import com.bysj.xl.chess.mychess.WebSocket.WsEvent.WsForgetPassSuccessEvent;
import com.bysj.xl.chess.mychess.entity.ReCheck;
import com.bysj.xl.chess.mychess.entity.Request;
import com.bysj.xl.chess.mychess.entity.TencentUser;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity {
    @BindView(R.id.modify_et_rePass)
    EditText modifyEtRepass;
    @BindView(R.id.modify_et_password)
    EditText modifyEtPass;
    @BindView(R.id.modify_bt_ensuren)
    Button modifyBtEnsure;
    @BindView(R.id.modify_bt_back)
    Button modifyBtBack;

    String phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initView() {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        phone = bundle.getString("phone");
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_modify_password);
    }

    @OnClick(R.id.modify_bt_ensuren)
    public void OnEnsureClick() {
        String pass = modifyEtPass.getText().toString().trim();
        String rePass = modifyEtRepass.getText().toString().trim();
        if (pass.isEmpty()) {
            myToast.ShowToastShort("请输入修改的密码");
            return;
        } else if (rePass.isEmpty()) {
            myToast.ShowToastShort("请输入确认密码");
            return;
        } else if (!rePass.equals(pass)) {
            myToast.ShowToastShort("两次密码输入不一致");
            return;
        }else if (!ReCheck.checkPassWord(pass)){
            myToast.ShowToastShort("密码格式错误");
            modifyEtPass.setText("");
            modifyEtRepass.setText("");
            return;
        }
        Request request = new Request();
        TencentUser user= new TencentUser();

        user.setUsr_passWord(pass);
        user.setUsr_phone(phone);
        request.setData(user);
        request.setUsr_request_type(C.FORGET_PASSWORD);
        request.setMsg("Modify the Password");
        wsInterface.sendMsg(new Gson().toJson(request));
    }

    @OnClick(R.id.modify_bt_back)
    public void OnBackClick() {
        toActivityWithFinish(ForgetActivity.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void OnForPassWordSuccessEvent(WsForgetPassSuccessEvent<TencentUser> event) {
        myToast.ShowToastShort("密码重置成功");
        toActivityWithFinish(LoginActivity.class);
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myToast.stopToast();

    }
}
