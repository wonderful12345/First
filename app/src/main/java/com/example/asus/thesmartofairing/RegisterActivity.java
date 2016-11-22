package com.example.asus.thesmartofairing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton btn_return;
    private Button btn_cancle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_return = (ImageButton) findViewById(R.id.btn_register_return);
        btn_return.setOnClickListener(this);
        btn_cancle = (Button) findViewById(R.id.btn_cancle);
        btn_cancle.setOnClickListener(this);

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
            default:
                break;
        }
    }
}
