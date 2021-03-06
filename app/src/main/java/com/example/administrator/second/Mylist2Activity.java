package com.example.administrator.second;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyRealList2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener,    {

    Handler handler;
    private ArrayList<HashMap<String,String>> listItems;//存放文字、图片信息
    private SimpleAdapter listItemAdapter;//适配器
    String TAG="MyList2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        this.setListAdapter(listItemAdapter);

        Thread thread=new Thread(this);
        thread.start();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    listItems=(List<HashMap<String,String>>)msg.obj;
                    listItemAdapter=new SimpleAdapter(MyRealList2Activity.this,listItems,
                            R.layout.list_item,
                            new String[]{"ItemTitle","ItemDetail"},
                            new int[]{R.id.itemTitle,R.id.itemDetail}
                    );
                    setListAdapter(listItemAdapter);

                }
                super.handleMessage(msg);
            }
        };
        //方法一：返回一行一行的列表。当列表被点击时监听
        /*getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

        //方法二：在运行如下代码之前需要先implement AdapterView.OnItemClickListener
        getListView().setOnItemClickListener(this);
    }

    private void initListView(){
        listItems=new ArrayList<HashMap<String, String>>();
        for(int i=0;i<10;i++){
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+ i);//标题文字
            map.put("ItemDetail","detail"+ i);//详情描述
            listItems.add(map);
        }
        //生成适配器的Item 和动态数组对应的元素
        listItemAdapter=new SimpleAdapter(this,listItems,//listItems 数据源
                //第二个参数是数据项
                R.layout.list_item,//ListItem 的XML 布局实现
                new String[]{"ItemTitle","ItemDetail"},
                //数据项里面的key
                new int[]{R.id.itemTitle,R.id.itemDetail}//listItems 里的 ItemTitle
                //会放在list_item 里的itemTitle 控件里（两两一一匹配）
                //布局里面的id
        );
    }

    @Override
    public void run() {
        List<HashMap<String,String>> retList=new ArrayList<HashMap<String, String>>();
        Document doc=null;
        try {

            Thread.sleep(3);
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"title:"+doc.title());
            Elements tables=doc.getElementsByTag("table");
            Element table1=tables.get(0);
            Elements tds=table1.getElementsByTag("td");
            for(int s=0;s<tds.size();s+=6){
                Element td1=tds.get(s);
                Element td2=tds.get(s+5);
                Log.i(TAG, "text="+td1.text()+" 折算价："+td2.text());
                String str1=td1.text();
                String val=td2.text();

                Log.i(TAG, "run: "+str1+"==>"+val);
                HashMap<String,String> map=new HashMap<String, String>();
                //然后往map 里面放内容
                map.put("ItemTitle",str1);
                map.put("ItemDetail",val);
                retList.add(map);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage();
        msg.what = 5;
        msg.obj=retList;
        handler.sendMessage(msg);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {//position 和id 在此处是一样的
        Log.i(TAG, "onItemClick: parents="+parent);
        Log.i(TAG, "onItemClick: view"+view);
        Log.i(TAG, "onItemClick: position"+position);
        Log.i(TAG, "onItemClick: id"+id);
        HashMap<String,String> map=(HashMap<String,String>)getListView().getItemAtPosition(position);
        String titleStr=map.get("ItemTitle");
        String detailStr=map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr"+titleStr);

        Log.i(TAG, "onItemClick: detailStr"+detailStr);

        TextView title=view.findViewById(R.id.itemTitle);
        TextView detail=view.findViewById(R.id.itemDetail);
        String title2= String.valueOf(title.getText());//和titleStr 是一样的内容
        String detail2= String.valueOf(detail.getText());
        Log.i(TAG, "onItemClick: title2"+title2);
        Log.i(TAG, "onItemClick: detail2"+detail2);

        //打开新的页面，传入参数
        Intent rateCalc=new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        //parseFloat 是转成float 基本类型，valueOf 转成Float 类
        startActivity(rateCalc);
    }
    @Override
    public boolean onItemLongClick(AdapterView<?>parent,View view,int position, long id){
        Log.i(TAG,"onItemLongClick:长按列表项position="+position)；
        //删除操作
       // listItems.remove(position);
        //listItemAdapter.notifyDataSetChanged();
        //构造对话框进行操作
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("请确认是否删除当前数据").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"onClick:对话框事件处理");
            }
        })
                .setNegativeButton("no",null);
        builder.create().show();
        Log.i(TAG,"onItemLongClick:size="+listItems.size());
        return true;
    }
}