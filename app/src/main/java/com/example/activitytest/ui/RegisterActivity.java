package com.example.activitytest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activitytest.R;
import com.example.activitytest.bean.UserInfo1;

import org.litepal.LitePal;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private EditText mPwdCheck;                       //密码编辑
    private Button mSureButton;                       //确定按钮
    private Button mCancelButton;                     //取消按钮

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //找到按钮
        mSureButton = (Button) findViewById(R.id.re_register);
        mCancelButton = (Button) findViewById(R.id.re_cancel);

        //找到editText的值
        mAccount = (EditText) findViewById(R.id.re_username);
        mPwd = (EditText) findViewById(R.id.re_password);
        mPwdCheck = (EditText) findViewById(R.id.re_affirm);
        //监听按钮
        mSureButton.setOnClickListener(this);      //注册界面两个按钮的监听事件
        mCancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_register:                       //确认按钮的监听事件
                register();
                break;
            case R.id.re_cancel:                     //取消按钮的监听事件,由注册界面返回登录界面
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    /**
     * 注册
     */
    public void register() {
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            long Id = System.currentTimeMillis();
            boolean isExist = LitePal.isExist(UserInfo1.class, "userName = ? ", userName);
            if (isExist) {//用户已经存在
                Toast.makeText(this, "用户已经存在，不能重复注册",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            UserInfo1 userInfo = new UserInfo1(userName, userPwd, Id);
            boolean flag = userInfo.save();//保存数据
            if (flag) {//保存数据成功
                Toast.makeText(this,"注册成功",
                        Toast.LENGTH_SHORT).show();
                Intent intent_Register_to_Login = new Intent(RegisterActivity.this, LoginActivity.class);    //切换User Activity至Login Activity
                startActivity(intent_Register_to_Login);
                finish();
            }else{
                Toast.makeText(this,"注册失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
        String userPwd = mPwd.getText().toString().trim();
        String Pwdcheck = mPwdCheck.getText().toString().trim();
        if (userName.equals("")) { //用户名为空
            Toast.makeText(this, "用户名不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (userPwd.equals("")) {
            Toast.makeText(this, "密码不能为空",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
            if (!userPwd.equals(Pwdcheck)) {        //两次输入密码不一致
                Toast.makeText(this, "两次密码输入不一致,请重新输入",
                        Toast.LENGTH_SHORT).show();
                return false;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}