package com.example.asus.thesmartofairing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btn_return;
    private Button btn_cancle;
    private Button btn_affirm;
    private EditText et_password1;
    private EditText et_ensure_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_return = (ImageButton) findViewById(R.id.btn_register_return);
        btn_return.setOnClickListener(this);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(this);
        btn_affirm = (Button) findViewById(R.id.btn_affirm);
        btn_affirm.setOnClickListener(this);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_ensure_password = (EditText) findViewById(R.id.et_sure_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register_return:
                finish();
                break;
            case R.id.btn_cancle:
                finish();
                break;
            case R.id.btn_affirm:
                String password1 = et_password1.getText().toString();
                String ensure_password = et_ensure_password.getText().toString();
                if (password1.equals(ensure_password)){

                }else {
                    Toast.makeText(this,"输入的密码不一致",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
