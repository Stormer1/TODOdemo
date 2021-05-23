package com.example.activitytest;

import android.app.AlarmManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.activitytest.bean.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

public class LongRunningService extends Service {

    private static final String TAG = "LongRunningService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        String name_1 = pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值
        List<Book> u = DataSupport.where("username = ?", name_1).find(Book.class);//返回的是一个对象<UserInfo1>
        Log.d(TAG, "666555  u: "+u);

        Book book = u.get(u.size()-1);

        long date= book.getNum();
        Log.d(TAG, "666555  date: "+date);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //读者可以修改此处的Minutes从而改变提醒间隔时间
        long minutes = date;
        Log.d(TAG, "666555  date: "+minutes);
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long triggerAtTime = SystemClock.elapsedRealtime() + minutes;
        //此处设置开启AlarmReceiver这个Service
//        Intent i = new Intent(this, SettingBroadcastReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        //ELAPSED_REALTIME_WAKEUP表示让定时任务的出发时间从系统开机算起，并且会唤醒CPU。
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        //在Service结束后关闭AlarmManager
//        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        Intent i = new Intent(this, SettingBroadcastReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.cancel(pi);
//    }
}
