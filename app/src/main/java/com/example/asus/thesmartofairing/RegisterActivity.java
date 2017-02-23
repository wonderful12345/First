package com.example.asus.thesmartofairing;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private EditText et_mobile_num;
    private DataBaseSQLHelp mDataBaseSQLHelp;
    private int YES = 1;
    private int NO = 0;
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
        et_mobile_num = (EditText) findViewById(R.id.et_Mobile_num);
        mDataBaseSQLHelp = new DataBaseSQLHelp(this,"Register.db",null,1);

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
                String mobile_num = et_mobile_num.getText().toString();
                int j=5;
                if (mobile_num.equals("")||ensure_password.equals("")){
                    Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
                }else {
                    if (password1.equals(ensure_password)){
                        if (password1.length()<6){
                            Toast.makeText(this,"密码的个数不符合要求",Toast.LENGTH_SHORT).show();
                        }else {
                            SQLiteDatabase db = mDataBaseSQLHelp.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            Cursor cursor =  db.query("Register",null,null,null,null,null,null);
                            if (cursor.moveToFirst()){
                                do {
                                    String s = cursor.getString(cursor.getColumnIndex("register_name"));
                                    String y = cursor.getString(cursor.getColumnIndex("register_password"));
                                    if (s.equals(mobile_num)){
                                        j=NO;
                                    }
                                    Log.d("MainActivity",s);
                                    Log.d("MainActivity",y);
                                }while (cursor.moveToNext());
                            }
                            if (j==NO){
                                Toast.makeText(this,"用户已经存在 注册失败",Toast.LENGTH_SHORT).show();
                            }else {
                                values.put("register_name",mobile_num);
                                values.put("register_password",ensure_password);
                                db.insert("Register",null,values);
                                values.clear();
                                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(this,Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }else {
                        Toast.makeText(this,"输入的密码不一致",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
