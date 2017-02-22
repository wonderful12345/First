package com.example.asus.thesmartofairing;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private TextView tv_casualsee;
    private Button btn_register;
    private EditText et_user;
    private EditText et_password;
    private Button btn_sign;
    private ImageView iv_user;
    private CheckBox cb_remmber;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private DataBaseSQLHelp mDataBaseSQLHelp;
    private int YES = 1;
    private int NO = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        mDataBaseSQLHelp = new DataBaseSQLHelp(this,"Register.db",null,1);
        String getuser = load("user");
        if (!TextUtils.isEmpty(getuser)){
            et_user.setText(getuser);
        }
        String getpassword = load("password");
        if (!TextUtils.isEmpty(getpassword)){
            et_password.setText(getpassword);
            et_password.setSelection(getpassword.length());
            cb_remmber.setChecked(true);
        }
    }

    private String load(String data) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput(data);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!=null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader !=null){
                try {
                    reader.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    private void initview() {
        tv_casualsee = (TextView) findViewById(R.id.tv_casualsee);
        btn_register = (Button) findViewById(R.id.btn_register);
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_sign = (Button) findViewById(R.id.btn_sign);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        cb_remmber = (CheckBox) findViewById(R.id.cb_checkBox);
        cb_remmber.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
        tv_casualsee.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        mSharedPreferences = getSharedPreferences("Login",MODE_PRIVATE);
        mEditor = getSharedPreferences("Login",MODE_PRIVATE).edit();
        mEditor.putString("boolean","false");
        mEditor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cb_remmber.isChecked()){
            String user1 = et_user.getText().toString();
            save(user1,"user");
            String password1 = et_password.getText().toString();
            save(password1,"password");
        }
    }


    private void save(String user,String data) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = openFileOutput(data, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(user);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign:
                String user = et_user.getText().toString();
                String password = et_password.getText().toString();
                SQLiteDatabase db = mDataBaseSQLHelp.getWritableDatabase();
                Cursor cursor = db.query("Register",null,null,null,null,null,null);
                int j = 8;
                if (cursor.moveToFirst()){
                    do{
                        String s1 = cursor.getString(cursor.getColumnIndex("register_name"));
                        String s2 = cursor.getString(cursor.getColumnIndex("register_password"));
                        if (s1.equals(user)&&s2.equals(password)){
                            j = YES;
                        }
                    }while (cursor.moveToNext());
                }
                if (j==YES){
                    Intent intent = new Intent();
                    intent.setClass(Login.this,MainActivity.class);
                    startActivity(intent);
                    mEditor.putString("boolean","true");
                    mEditor.putString("user",user);
                    mEditor.commit();
                    finish();
                } else {
                    mEditor.putString("boolean","false");
                    mEditor.commit();
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
