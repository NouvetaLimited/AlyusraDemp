package com.glackemi.alyusra;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_post;
    Button btn_check;
    Button btn_pay;
    int me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = 0;
        if(me == 1){
            setContentView(R.layout.activity_main);
            btn_post = (Button) findViewById(R.id.btn_post);
            btn_check = (Button) findViewById(R.id.btn_check);
            btn_pay =  (Button) findViewById(R.id.btn_pay);

            btn_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, CheckTransaction.class));
                }
            });

            btn_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, MakeOrder.class));
                }
            });

            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, payActivity.class));
                }
            });
        }
        else{
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        }

}
