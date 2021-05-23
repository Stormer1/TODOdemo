package com.example.activitytest.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.activitytest.R;
import com.example.activitytest.bean.Book;
import com.example.activitytest.utils.Adapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private ArrayList<Book> list = new ArrayList<>();
    private AlertDialog dialog;
    private Adapter adapter;
    private  SettingBroadcastReceiver settingBroadcastReceiver;
    private static final String TAG = "TodoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setSubtitle("明日晨曦，还将与你相遇");
        actionbar.setTitle(" Your TODO");

         settingBroadcastReceiver = new SettingBroadcastReceiver();//动态广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.test");
        registerReceiver(settingBroadcastReceiver, intentFilter);


        setContentView(R.layout.activity_todo);
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        String name_1 = pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值


        ListView listView = findViewById(R.id.list_view);

        final List<Book> books = DataSupport.where("username = ?", name_1).find(Book.class);
        list.addAll(books);

        adapter = new Adapter(this, list);

        Log.d(TAG, "abc  onCreate: "+ books);

        listView.setAdapter(adapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
//            String name =pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(TodoActivity.this);
                dialog.setTitle("确定删除此备忘录嘛？");
                dialog.setMessage("Maybe Something important");
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = list.get(position).getId();
                        if(list.remove(position)!=null){
                            System.out.println("success");
                        }else {
                            System.out.println("failed");
                        }
                        deleteData(id);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getBaseContext(), "叮~~删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.create().show();
                return true;
            }

            public void deleteData(int id){
                DataSupport.delete(Book.class,id);
            }
        });

        //点击修改备忘录内容
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id_2 = list.get(position).getId();
                Intent intent =new Intent(TodoActivity.this, EditItemActivity.class);
                intent.putExtra("id",id_2);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        setContentView(R.layout.activity_todo);
        SharedPreferences pref = getSharedPreferences("data_s",MODE_PRIVATE);
        String name_1 = pref.getString("username","");//后面“”是如果找不到String就返回第二个参数指定的默认的值
        final List<Book> books = DataSupport.where("username = ?", name_1).find(Book.class);
        list.clear();
        list.addAll(books);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(TodoActivity.this, AddTodoActivity.class);
                startActivity(intent);
                break;

            case R.id.back:
                Intent intent_2 = new Intent(TodoActivity.this, LoginActivity.class);
                startActivity(intent_2);
                finish();
                break;

            case R.id.check_name:
                Intent intent_1 = new Intent(TodoActivity.this, PersonalActivity.class);
                startActivity(intent_1);
                break;
            default:
        }
        return true;
    }


    //内部类：动态广播
        class SettingBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Notification notification = new NotificationCompat.Builder(context, "chat")
                    /**设置通知左边的大图标**/
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    /**设置通知右边的小图标**/
                    .setSmallIcon(R.mipmap.ic_launcher)
                    /**通知首次出现在通知栏，带上升动画效果的**/
                    .setTicker("TODO")
                    /**设置通知的标题**/
                    .setContentTitle("TODO")
                    /**设置通知的内容**/
                    .setContentText("设置的内容完成没哇？点击进入\"执笔\"查看")
                    /**通知产生的时间，会在通知信息里显示**/
                    .setWhen(System.currentTimeMillis())
//                /**设置该通知优先级**/
//                .setPriority(Notification.PRIORITY_DEFAULT)
                    /**设置这个标志当用户单击面板就可以让通知将自动取消**/
                    .setAutoCancel(true)
//                /**设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)**/
//                .setOngoing(false)
//                /**向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：**/
//                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND)
                    .setContentIntent(PendingIntent.getActivity(context, 1, new Intent(context, TodoActivity.class), PendingIntent.FLAG_CANCEL_CURRENT))
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            /**发起通知**/
            notificationManager.notify(1, notification);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (settingBroadcastReceiver!=null){
            unregisterReceiver(settingBroadcastReceiver);
            settingBroadcastReceiver = null;
        }


    }
}
