package com.example.administrator.second;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThirdActivity extends AppCompatActivity  implements Runnable{

    private final String TAG="Rate";
    private float dollarrate =0.0f;
    private float eurorate =0.0f;
    private float wonrate =0.0f;


    EditText rmb;
    TextView showout;
    Handler handler;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        showout=findViewById(R.id.show_out);
        rmb=findViewById(R.id.input);

        //获取SP里保存的数据
        SharedPreferences app = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
        dollarrate=app.getFloat("dollar_rate",0.0f);
        eurorate=app.getFloat("euro_rate",0.0f);
        wonrate=app.getFloat("won_rate",0.0f);
        Log.i(TAG,"onCreate:sp dollarrate="+dollarrate);
        Log.i(TAG,"onCreate:sp eurorate="+eurorate);
        Log.i(TAG,"onCreate:sp wonrate="+wonrate);

        //开启子线程
        Thread t=new Thread(this);
        t.start();
    }

    public void onClick(View view) {
        String str = rmb.getText().toString();
        float r=0;
        if (str.length()>0){
        r=Float.parseFloat(str);
        }
        else Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        if (view.getId()==R.id.but_dollar){
            showout.setText(String.format("%.2f",r*dollarrate));
        }
        else if(view.getId()==R.id.but_euro){
            showout.setText(String.format("%.2f",r*eurorate));
        }
        else {
            showout.setText(String.format("%.2f",r*wonrate));
        }
    }
    public void openOne(View btn){
        Intent config =new Intent(this,ConfigActivity.class);

        config.putExtra("dollar_rate",dollarrate);
        config.putExtra("euro_rate",eurorate);
        config.putExtra("won_rate",wonrate);

        Log.i(TAG,"openOne:dollarrate"+dollarrate);
        Log.i(TAG,"openOne:dollarrate"+eurorate);
        Log.i(TAG,"openOne:dollarrate"+wonrate);

        //startActivity(config);
        startActivityForResult(config,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==5){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handlemessage:getmessage msg="+str);
                    showout.setText(str);
                }
                super.handleMessage(msg);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_set) {
            Intent config = new Intent(this, ConfigActivity.class);

            config.putExtra("dollar_rate", dollarrate);
            config.putExtra("euro_rate", eurorate);
            config.putExtra("won_rate", wonrate);

            Log.i(TAG, "openOne:dollarrate" + dollarrate);
            Log.i(TAG, "openOne:dollarrate" + eurorate);
            Log.i(TAG, "openOne:dollarrate" + wonrate);

            //startActivity(config);
            startActivityForResult(config, 1);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==2){
              Bundle bd= data.getExtras();
              dollarrate = bd.getFloat("key_dollar",0.1f);
              eurorate= bd.getFloat("key_euro",0.1f);
              wonrate= bd.getFloat("key_won",0.1f);
              //将新设的汇率写道SP里
            SharedPreferences app = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor edi= app.edit();
            edi.putFloat("dollarrate_rate",dollarrate);
            edi.putFloat("eurorate_rate",eurorate);
            edi.putFloat("wonrate_rate",wonrate);
            edi.commit();
            Log.i(TAG,"onActivityResult:数据已保存到SP");

        }
    }

@Override
public void run() {
        Log.i(TAG, "run:run()....");
        for (int i = 1; 1 < 6; i++) {
            Log.i(TAG, "run=i" + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //获取Msg对象，用于返回主线程
    Message msg =handler.obtainMessage(1);
    msg.obj="Hello from run()";
     handler.sendMessage(msg);
     //获取网络数据
    URL url= null;
    try {
        url= new URL("http://www.usd-cny.com/bankofchina.htm");
        HttpURLConnection http= (HttpURLConnection) url.openConnection();
        InputStream in =http.getInputStream();


    }
    catch (MalformedURLException e){
        e.printStackTrace();
    }
    catch (IOException e) {
        e.printStackTrace();
    }

}
}
  private String inputStrem
//获取SP里保存的数据

//获取
handler =(Hangler)handleMessage(msg)