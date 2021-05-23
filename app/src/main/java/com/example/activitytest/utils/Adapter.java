package com.example.activitytest.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.activitytest.R;
import com.example.activitytest.bean.Book;

import java.util.List;

public class Adapter extends BaseAdapter {

    List<Book> list;
    // 布局加载器
    LayoutInflater inflater;

    private static final String TAG = "Adapter";

    public Adapter(Context context, List<Book> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
     }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item,null);

        TextView title1 = view.findViewById(R.id.text_item);
        TextView title2 = view.findViewById(R.id.text_date);
        TextView title3 = view.findViewById(R.id.text_time);

        String s_1 = list.get(position).getName();
        String s_2 = list.get(position).getDate();
        String s_3 = list.get(position).getTime();

        title1.setText(s_1);
        title2.setText("#"+ s_2);
        title3.setText(s_3);

        return view;
    }
}
