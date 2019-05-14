package com.example.administrator.second;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class point extends AppCompatActivity {
   TextView point1;
   TextView point2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        point1 =(TextView)findViewById(R.id.point1);
        point2 =(TextView)findViewById(R.id.point2);
    }

    public void btnADD1(View view) {
        if (view.getId()==R.id.btn2ADD1)
            showScore(1);
         else{
             showScore2(1);
        }

    }

    public void btnADD2(View view) {
        if (view.getId()==R.id.btn2ADD2)
            showScore(2);
        else{
            showScore2(2);
        }

    }

    public void btnADD3(View view) {
        if (view.getId()==R.id.btn2ADD3)
            showScore(3);
        else{
            showScore2(3);
        }
    }

    private void showScore(int inc){
        Log.i("show","inc="+inc);
        String oldScore=(String) point1.getText();
        int newScore=Integer.parseInt(oldScore)+inc;
        point1.setText(""+newScore);
    }
    private void showScore2(int inc){
        Log.i("show","inc="+inc);
        String oldScore=(String) point2.getText();
        int newScore=Integer.parseInt(oldScore)+inc;
        point2.setText(""+newScore);
    }

    public void reset(View view) {
        point1.setText("0");
        point2.setText("0");

    }
}
