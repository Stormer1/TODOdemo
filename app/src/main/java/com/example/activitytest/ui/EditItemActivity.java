package com.example.activitytest.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.activitytest.R;
import com.example.activitytest.bean.Book;

import org.litepal.crud.DataSupport;

import java.util.List;

public class EditItemActivity extends AppCompatActivity {
    private EditText editText_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeButtonEnabled(true);
        actionbar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id",1);
        SharedPreferences pref = getSharedPreferences("data_s", MODE_PRIVATE);
        final String name_1 = pref.getString("username", "");//后面“”是如果找不到String就返回第二个参数指定的默认的值
        editText_1 = findViewById(R.id.edit_item);

        List<Book> c = DataSupport.where("username = ? and id = ?", name_1,""+id).find(Book.class);//返回的是一个对象<c>
        final Book edit_2 = c.get(0);

        editText_1.setText(edit_2.getName());//将原来内容放置进入修改框中

        Button button_edit_item = findViewById(R.id.button_edititem);
        button_edit_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){


                edit_2.setName(editText_1.getText().toString());
                edit_2.setId(id);
                edit_2.setUsername(name_1);
                edit_2.save();
                Toast.makeText(EditItemActivity.this, "修改成功~", Toast.LENGTH_LONG).show();
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

