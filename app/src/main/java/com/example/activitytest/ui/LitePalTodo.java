package com.example.activitytest.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.activitytest.R;
import com.example.activitytest.bean.Book;

import org.litepal.tablemanager.Connector;

public class LitePalTodo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lite_pal);
            Button createDatabase = (Button)findViewById(R.id.create_database);
            createDatabase.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Connector.getDatabase();
                }
            });
    }
}


