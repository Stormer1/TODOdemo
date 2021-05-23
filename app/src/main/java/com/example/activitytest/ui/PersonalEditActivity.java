package com.example.activitytest.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.activitytest.R;
import com.example.activitytest.bean.UserInfo1;

import org.litepal.crud.DataSupport;

import java.util.List;

public class PersonalEditActivity extends AppCompatActivity {

    private EditText your_name;
    private EditText your_motto;
    private EditText your_QQ;
    private EditText your_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        final String name_1=pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值
        setContentView(R.layout.personal_edit);

        your_name= findViewById(R.id.your_name);
        your_motto = findViewById(R.id.your_motto);
        your_QQ = findViewById(R.id.your_QQ);
        your_phone = findViewById(R.id.your_phone);
        Button button_saveall = findViewById(R.id.button_saveall);

        List<UserInfo1> u = DataSupport.where("username = ?", name_1).find(UserInfo1.class);//返回的是一个对象<UserInfo1>
        UserInfo1 userInfo = u.get(0);

        your_name.setText(userInfo.getPersonal());
        your_motto.setText(userInfo.getmotto());
        your_QQ.setText(userInfo.getQQ());
        your_phone.setText(userInfo.getPhone());


        button_saveall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<UserInfo1> u = DataSupport.where("username = ?", name_1).find(UserInfo1.class);//返回的是一个对象<UserInfo1>

               UserInfo1 edit = u.get(0);

               edit.setPersonal(your_name.getText().toString());
               edit.setQQ(your_QQ.getText().toString());
               edit.setmotto(your_motto.getText().toString());
               edit.setPhone(your_phone.getText().toString());
               edit.setUsername(name_1);
               edit.save();
               finish();

            }
        });
        }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}

