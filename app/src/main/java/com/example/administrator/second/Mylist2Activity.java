package com.example.administrator.second;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class Mylist2Activity extends ListActivity {
    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist2);
        initiListView();
        this.setListAdapter(listItemAdapter);
        Thread t=new Thread(this);
        t.start();
    }
    private void initiListView(){
        listItems= new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String>map=new HashMap<String, String>();
            map.put("ItemTitle","rate:"+i);//标题文字
            map.put("ItemDetail","detail:"+i);//详情描述
            listItems.add(map);
        }
        //生成适配器的Item和动态数组对应元素
        listItemAdapter =new SimpleAdapter(this,listItems,//数据源
                R.layout.activity_mylist2,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail});
    }
}
