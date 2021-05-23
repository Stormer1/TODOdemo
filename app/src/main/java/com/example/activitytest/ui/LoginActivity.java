package com.example.activitytest.ui;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activitytest.R;
import com.example.activitytest.bean.UserInfo1;

import org.litepal.LitePal;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText mAccount;                        //用户名编辑
    private EditText mPwd;                            //密码编辑
    private CheckBox rememberCheck;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        rememberCheck = findViewById(R.id.et_issave);
        mAccount = findViewById(R.id.title_title);//通过id找到账号密码
        mPwd = findViewById(R.id.title_password);
        final Button button_pass = findViewById(R.id.button_pass);
        pref = getSharedPreferences("data_s",MODE_PRIVATE);//创建一个sharedPreference;名字：data_s
        editor = pref.edit();

        ActionBar actionbar = getSupportActionBar();//隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
        String user = pref.getString("username",""); //从share。。。读取用户名
        mAccount.setText(user);
        boolean isRemember = pref.getBoolean("remember_pass",false);//实现记住密码
        if (isRemember){
            // 将密码都设置到文本框中
            String pwd = pref.getString("userPwd","");
            mPwd.setText(pwd);
            rememberCheck.setChecked(true);
        }

        button_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressBarDialog = new ProgressDialog
                        (LoginActivity.this);
                progressBarDialog.setTitle("搬砖ing");
                progressBarDialog.setMessage("loading");
                progressBarDialog.setCancelable(true);
                progressBarDialog.show();

                String user = mAccount.getText().toString().trim();//获取用户名
                String pwd = mPwd.getText().toString().trim();     //获取密码


                editor.putString("username", user);//括号里面是share...的key ，user（数据）
                editor.apply();//保存数据进share。。。

                List<UserInfo1> list = LitePal.where("username = ? and userPwd = ?", user, pwd).find(UserInfo1.class);//判断账号密码是否匹配
                List<UserInfo1> list_1 = LitePal.where("username = ? ", user).find(UserInfo1.class);
                List<UserInfo1> list_2 = LitePal.where("username = ? and userPwd != ?", user, pwd).find(UserInfo1.class);

                if (list.size() > 0) {//登录成功
                    if (rememberCheck.isChecked()) {
                        editor = pref.edit();

                        // 保存数据到SharePreference文件中
                        editor.putBoolean("remember_pass", true);
                        editor.putString("username", user);
                        editor.putString("userPwd", pwd);
                        editor.apply();
                    } else {
                        editor.clear();
                        editor.putString("username", user);
                        editor.apply();
                    }

                    Intent intent = new Intent(LoginActivity.this, TodoActivity.class);
                    startActivity(intent);
                    progressBarDialog.cancel();
                    //切换界面
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                  finish();
                } else if (list_1.size() == 0) {//用户名登录失败
                    Toast.makeText(LoginActivity.this, "用户名不存在，请注册哦", Toast.LENGTH_SHORT).show();
                    progressBarDialog.cancel();
                }
                else if (list_2.size() > 0) {//密码登录失败
                    Toast.makeText(LoginActivity.this, "密码错误，请重试", Toast.LENGTH_SHORT).show();
                    progressBarDialog.cancel();
                }
            }
        });

        Button button_register = findViewById(R.id.button_register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}