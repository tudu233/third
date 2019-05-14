package com.example.administrator.second;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class ConfigActivity extends AppCompatActivity {
    public final String TAG="ConfigActivity";

    EditText dollar_text;
    EditText euro_text;
    EditText won_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent=getIntent();
        float dollar2 = intent.getFloatExtra("dollar_rate",0.0f);
        float euro2 = intent.getFloatExtra("euro_rate",0.0f);
        float won2 = intent.getFloatExtra("won_rate",0.0f);

        Log.i(TAG,"onCreate:dollar2="+dollar2);
        Log.i(TAG,"onCreate:euro2="+euro2);
        Log.i(TAG,"onCreate:won2="+won2);

        dollar_text=(EditText)findViewById(R.id.dollar_rate);
        euro_text=(EditText)findViewById(R.id.euro_rate);
        won_text=(EditText)findViewById(R.id.won_rate);

        //显示数据到控件

        dollar_text.setText(String.valueOf(dollar2));
        euro_text.setText(String.valueOf(euro2));
        won_text.setText(String.valueOf(won2));
    }
    public void save(View btn){
        Log.i(TAG,"save");


        //获取新的值
        float newDollar = Float.parseFloat(dollar_text.getText().toString());
        float newEuro = Float.parseFloat(euro_text.getText().toString());
        float newWon = Float.parseFloat(won_text.getText().toString());

        Log.i(TAG,"onCreate:newDollar="+newDollar);
        Log.i(TAG,"onCreate:newEuro="+newEuro);
        Log.i(TAG,"onCreate:newWon="+newWon);

        //保存到bundle或放入extra
        Intent intent=getIntent();
        Bundle bd= new Bundle();
        bd.putFloat("key_dollar",newDollar);
        bd.putFloat("key_euro",newEuro);
        bd.putFloat("key_won",newWon);
         intent.putExtras(bd);



        //返回到调用界面
        setResult(2,intent);
    }
}
