package com.example.asus.thesmartofairing;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_casualsee;
    private Button btn_register;
    private EditText et_user;
    private EditText et_password;
    private Button btn_sign;
    private ImageView iv_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
    }

    private void initview() {
        tv_casualsee = (TextView) findViewById(R.id.tv_casualsee);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        btn_sign.setOnClickListener(this);
        tv_casualsee.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign:
                String user = et_user.getText().toString();
                String password = et_password.getText().toString();
                if (user.equals("13794575196")&&password.equals("yang")){
                    Intent intent = new Intent();
                    intent.setClass(Login.this,MainActivity.class);
                    startActivity(intent);
                    //iv_user.setBackground(getDrawable(R.drawable.t019c43ed4eab6ebf26));
                    finish();
                }else {
                    Toast.makeText(this,"你输入的账号或密码有误",Toast.LENGTH_LONG).show();
                }
                break;
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
