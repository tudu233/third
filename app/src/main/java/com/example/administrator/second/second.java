package com.example.administrator.second;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class second extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.second);

        TextView out = findViewById(R.id.textView );
        out.setText(getString(R.string.res));

        EditText input =findViewById(R.id.editText );
        String str=input.getText() .toString();

        Button bt =findViewById(R.id.button);
        bt.setOnClickListener(this);

        TextView res= findViewById(R.id.textView2 );
        res.setText("");
    }

    @Override
    public void onClick(View v) {
        EditText input =findViewById(R.id.editText );
        String str=input.getText() .toString();
        double num=Double.parseDouble(str);
        double resnum=(num-32)/1.8;
        TextView res = findViewById(R.id.textView2 );
        res.setText(String.valueOf(resnum) );

    }
}
