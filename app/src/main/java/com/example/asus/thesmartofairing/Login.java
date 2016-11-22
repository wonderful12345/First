package com.example.asus.thesmartofairing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_casualsee;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tv_casualsee = (TextView) findViewById(R.id.tv_casualsee);
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_casualsee.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_casualsee:
                Intent intent = new Intent();
                intent.setClass(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent();
                intent1.setClass(Login.this,RegisterActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }
}
