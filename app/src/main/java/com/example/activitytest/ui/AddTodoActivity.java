package com.example.activitytest.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.activitytest.bean.Book;
import com.example.activitytest.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTodoActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textDate;
    private SimpleDateFormat simpleDateFormat;
    private Intent intent;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private long num_1, num;
    private NotificationManager manager;
    private CheckBox rememberCheck_2;
    private static final String TAG = "Test";
    private Calendar calendar;
    private Calendar c;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);

        SharedPreferences pref = getSharedPreferences("data_s", MODE_PRIVATE);
        final String name_1 = pref.getString("username", "");//后面“”是如果找不到String就返回第二个参数指定的默认的值

        setContentView(R.layout.activity_add_todo);
        editText = findViewById(R.id.edit_1);//每个item内容
        textDate = findViewById(R.id.edit_date);//自己定的闹钟时间
        Button button_save = findViewById(R.id.button_save);
        rememberCheck_2 = findViewById(R.id.set_alarm);
        calendar = Calendar.getInstance();


        //设置渠道
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        String channelId = "chat";
        String channelName = "聊天消息";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        createNotificationChannel(channelId, channelName, importance);

        DatePicker datePicker = (DatePicker)
                findViewById(R.id.datePicker);
        TimePicker timePicker = (TimePicker)
                findViewById(R.id.timePicker);
        // 获取当前的年、月、日、小时、分钟
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        num = Calendar.getInstance().getTimeInMillis();//获取当前时间

        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker arg0, int setyear
                    , int setmonth, int setday) {
                year = setyear;
                month = setmonth;
                day = setday;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
            }
        });
        timePicker.setEnabled(true);
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minuteofDay) {
                hour = hourOfDay;
                minute = minuteofDay;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                calendar.set(Calendar.HOUR, hourOfDay);
                calendar.set(Calendar.MINUTE, minuteofDay);
//                calendar.set(Calendar.SECOND,0);
//                calendar.set(Calendar.MILLISECOND,0);
                if ((minuteofDay - c.get(Calendar.MINUTE)) % 2 == 1) {
                    num_1 = calendar.getTimeInMillis() - num - 43199997;
                } else {
                    num_1 = calendar.getTimeInMillis() - num;
                }

            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onClick(View v) {

                if (num_1 > -2) {
                    if (rememberCheck_2.isChecked()) {

//                        //开启关闭Service
//                        startService(intent);
//                        //设置一个Toast来提醒使用者提醒的功能已经开始

                        SharedPreferences pref = getSharedPreferences("data_s", MODE_PRIVATE);
                        String name_1 = pref.getString("username", "");//后面“”是如果找不到String就返回第二个参数指定的默认的值
                        List<Book> u = LitePal.where("username = ?", name_1).find(Book.class);//返回的是一个对象<UserInfo1>
                        Log.d(TAG, "666555  u: " + u);

                        long dateB = num_1;

                        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);  //读者可以修改此处的Minutes从而改变提醒间隔时间
                        long minutes = dateB;
                        Log.d(TAG, "666555  date: " + minutes); //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
                        long triggerAtTime = SystemClock.elapsedRealtime() + minutes;  //此处设置开启AlarmReceiver这个Service
                        Intent i = new Intent();
                        i.setAction("android.test");
                        PendingIntent pi = PendingIntent.getBroadcast(AddTodoActivity.this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT); //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
                        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);

                        Toast.makeText(AddTodoActivity.this, "已开启提醒，提醒将以消息弹窗显示", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddTodoActivity.this, "时间设置好像有点问题呢，请重新设置", Toast.LENGTH_SHORT).show();
                    return;
                }

                simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// 显示创建todo的年月日HH:mm:ss
                Date date = new Date(System.currentTimeMillis());
                Book book = new Book();
                book.setTime("#创建于" + simpleDateFormat.format(date));
                book.setName(editText.getText().toString());
                book.setDate(textDate.getText().toString());
                book.setUsername(name_1);
//                book.setNum(num_1);
                book.save();

                finish();
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showDate(int year, int month
            , int day, int hour, int minute) {
        TextView show = (TextView) findViewById(R.id.edit_date);
        show.setText("在" + year + "年" + (month + 1) + "月" + day + "日  " + hour + "时" + minute + "分之前完成哦");
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在Activity被关闭后，关闭Service
//        stopService(intent);
//    }



}

