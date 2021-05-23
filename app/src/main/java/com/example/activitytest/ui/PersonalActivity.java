package com.example.activitytest.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.activitytest.R;
import com.example.activitytest.bean.UserInfo1;

import org.litepal.crud.DataSupport;

import java.util.List;


public class PersonalActivity extends AppCompatActivity {
    private static final String TAG = "PersonalActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        String name_1 = pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值

        TextView text_1= findViewById(R.id.text_name );
        TextView text_2= findViewById(R.id.text_motto );
        TextView text_3= findViewById(R.id.text_QQ );
        TextView text_4= findViewById(R.id.text_Phone );

        List<UserInfo1> u = DataSupport.where("username = ?", name_1).find(UserInfo1.class);//返回的是一个对象<UserInfo1>

        UserInfo1 userInfo = u.get(0);

        text_1.setText(userInfo.getPersonal());
        text_2.setText(userInfo.getmotto());
        text_3.setText(userInfo.getQQ());
        text_4.setText(userInfo.getPhone());

        Button button_EDIT = findViewById(R.id.button_EDIT);
        button_EDIT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(PersonalActivity.this, PersonalEditActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        String name_1 = pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值
        List<UserInfo1> u = DataSupport.where("username = ?", name_1).find(UserInfo1.class);//返回的是一个对象<UserInfo1>
        UserInfo1 userInfo = u.get(0);

        TextView text_1= findViewById(R.id.text_name );
        TextView text_2= findViewById(R.id.text_motto );
        TextView text_3= findViewById(R.id.text_QQ );
        TextView text_4= findViewById(R.id.text_Phone );

        text_1.setText(userInfo.getPersonal());
        text_2.setText(userInfo.getmotto());
        text_3.setText(userInfo.getQQ());
        text_4.setText(userInfo.getPhone());
    }
}
