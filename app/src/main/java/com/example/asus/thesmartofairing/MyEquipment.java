package com.example.asus.thesmartofairing;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyEquipment extends Fragment implements View.OnClickListener{
    private TextView tv_time;
    private TextView tv_light;
    private TextView tv_win;
    private TextView tv_look;
    private TextView tv_way;
    private TextView tv_set;
    private ImageButton ib_up;
    private ImageButton ib_left;
    private ImageButton ib_right;
    private ImageButton ib_down;
    private View mView;
    private EditText et_hour;
    private EditText et_minute;
    private Button btn_light_open;
    private Button btn_light_close;
    private SeekBar sb_light;
    private SeekBar sb_win;
    private SeekBar sb_way_up;
    private SeekBar sb_way_out;
    private SeekBar sb_way_in;
    private SeekBar sb_way_down;
    private TextView tv_light_progress;
    private TextView tv_win_progress;
    private TextView tv_up_progress;
    private TextView tv_out_progress;
    private TextView tv_down_progress;
    private TextView tv_in_progress;
    private String line;
    private Button btn_win_work;
    private Button btn_win_close;
    private View view;
    private static final int CONNECTED = 1;
    private static final int RECEIVE = 0;
    private SharedPreferences.Editor editor1;
    private SharedPreferences mSharedPreferences;
    private TimeCount mTimeCount;
    private TextView set_time_last;
    private TextView set_time_open;
    private TextView set_time_shutdown;


    private static final String HOST = "192.168.1.135";
    private static final int PORT = 9991;

    private BufferedWriter mWriter;
    private BufferedReader mReader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_equipment,container,false);
        // Inflate the layout for this fragment
        initButton();
        return mView;
    }

    private void initButton() {
        tv_time = (TextView) mView.findViewById(R.id.tv_time);
        tv_time.setOnClickListener(this);
        tv_light = (TextView) mView.findViewById(R.id.tv_light);
        tv_light.setOnClickListener(this);
        tv_win = (TextView) mView.findViewById(R.id.tv_win);
        tv_win.setOnClickListener(this);
        tv_look = (TextView) mView.findViewById(R.id.tv_look);
        tv_look.setOnClickListener(this);
        tv_way = (TextView) mView.findViewById(R.id.tv_way);
        tv_way.setOnClickListener(this);
        tv_set = (TextView) mView.findViewById(R.id.tv_set);
        tv_set.setOnClickListener(this);
    }

    private void SendMsg(Socket socket, String sendtext) throws IOException {
        mWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        mWriter.write(sendtext.replace("\n", "") +"\n");
        mWriter.flush();
    }

    private String ReceiveMsg(Socket socket) throws IOException {
        mReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        String txt = "";
        while ((line = mReader.readLine())!=null){
            txt += line+"\n";
        }
        mReader.close();
        return txt;
    }
    private void Connect(Socket socket1){
        if(socket1.isConnected()){
            Message message = new Message();
            message.what = CONNECTED;
            handler.sendMessage(message);
        }
    }

    private Handler handler = new Handler() {
        @Override
        // 当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CONNECTED:
                    Log.d("MMMMMMM","CONNECT");
                    break;
                case RECEIVE:
                    editor1.putString("boolean","true");
                    editor1.commit();
                    break;
            }
        }
    };

    private void ReceiverListener(final String string){
            new Thread() {
                @Override
                public void run() {
                    String h = string;
                    String s=string;
                    // 执行完毕后给handler发送一个空消息
                    try {
                        // 实例化Socket
                        Socket socket = new Socket(HOST, PORT);
                        Connect(socket);
                        SendMsg(socket,s);
                        s = ReceiveMsg(socket);
                        if (s!=null){
                            mWriter.close();
                            socket.close();
                            if (h.equals("K")){
                                editor1.putString("K",s);
                                editor1.commit();
                            }
                            Message message1 = new Message();
                            message1.what = RECEIVE;
                            handler.sendMessage(message1);
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
    }
    @Override
    public void onClick(View v) {
        mSharedPreferences = getActivity().getSharedPreferences("Receive",Context.MODE_PRIVATE);
        editor1 = getActivity().getSharedPreferences("Receive",Context.MODE_PRIVATE).edit();
        switch (v.getId()){
            case R.id.tv_time:
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                view = layoutInflater.inflate(R.layout.set_time_alertdio,null);
                new AlertDialog.Builder(getActivity()).setTitle("请输入时间").setIcon(R.drawable.give_me_time).setView(view).show();
                set_time_open = (TextView) view.findViewById(R.id.tv_set_time_open);
                set_time_last = (TextView) view.findViewById(R.id.set_time_last);
                et_hour = (EditText) view.findViewById(R.id.et_hour);
                et_minute = (EditText) view.findViewById(R.id.et_minute);
                set_time_shutdown = (TextView) view.findViewById(R.id.set_time_shutdown);
                set_time_open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String hour = et_hour.getText().toString();
                        String minute = et_minute.getText().toString();
                        if (hour.equals("")){
                            hour = "0";
                        }
                        if (minute.equals("")){
                            minute = "0";
                        }
                        int h = Integer.valueOf(hour).intValue();
                        int m = Integer.valueOf(minute).intValue();
                        int sum = h*3600+m*60;
                        mTimeCount = new TimeCount(sum*1000,1000);
                        mTimeCount.start();
                    }
                });
                set_time_shutdown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTimeCount.cancel();
                        set_time_last.setText("0秒");
                        et_hour.setInputType(InputType.TYPE_CLASS_TEXT);
                        et_minute.setInputType(InputType.TYPE_CLASS_TEXT);

                    }
                });
                break;
            case R.id.tv_light:
                LayoutInflater layoutInflater1 = LayoutInflater.from(getActivity());
                View view = layoutInflater1.inflate(R.layout.set_light_alertdiao,null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.set_light_icon).setTitle("紫外灯设置").setView(view).show();
                btn_light_open = (Button) view.findViewById(R.id.btn_light_open);
                btn_light_close = (Button) view.findViewById(R.id.btn_light_close);
                editor1.putString("boolean","false");
                editor1.commit();
                btn_light_open.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String light_open_string = "O";
                        ReceiverListener(light_open_string);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String receive = null;
                                receive = mSharedPreferences.getString("boolean","");
                                if (receive.equals("true")){
                                    Toast.makeText(getActivity(),"紫外灯打开成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"紫外灯打开失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },1000);
                        alertDialog.cancel();
                    }
                });
                btn_light_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String light_close_string = "C";
                        ReceiverListener(light_close_string);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String receive = null;
                                receive = mSharedPreferences.getString("boolean","");
                                if (receive.equals("true")){
                                    Toast.makeText(getActivity(),"紫外灯关闭成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"紫外灯关闭失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },1000);
                        alertDialog.cancel();
                    }
                });
                break;
            case R.id.tv_win:
                String win = "give me win";
                ReceiverListener(win);
                LayoutInflater layoutInflater2 = LayoutInflater.from(getActivity());
                view = layoutInflater2.inflate(R.layout.set_win_alertdiao,null);
                final AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity()).setIcon(R.drawable.set_win_icon).setTitle("风干设置").setView(view).show();
                btn_win_work= (Button) view.findViewById(R.id.btn_win_work);
                btn_win_close = (Button) view.findViewById(R.id.btn_win_close);
                editor1.putString("boolean","false");
                editor1.commit();
                btn_win_work.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String win_work_string = "work";
                        ReceiverListener(win_work_string);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String receive = null;
                                receive = mSharedPreferences.getString("boolean","");
                                if (receive.equals("true")){
                                    Toast.makeText(getActivity(),"风干开始工作",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"风干打开失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },1000);
                        alertDialog1.cancel();
                    }
                });
                btn_win_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String win_stop_string = "stop";
                        ReceiverListener(win_stop_string);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String receive = null;
                                receive = mSharedPreferences.getString("boolean","");
                                if (receive.equals("true")){
                                    Toast.makeText(getActivity(),"成功停止风干",Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(getActivity(),"停止风干失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },1000);
                        alertDialog1.cancel();
                    }
                });

                break;
            case R.id.tv_look:
                String look = "K";
                ReceiverListener(look);
                LayoutInflater inflater3 = LayoutInflater.from(getActivity());
                view = inflater3.inflate(R.layout.set_look_alertdialog,null);
                new AlertDialog.Builder(getActivity()).setTitle("查看衣杆状态").setIcon(R.drawable.alert_look).setView(view).show();
                break;
            case R.id.tv_way:
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                view = inflater.inflate(R.layout.set_way_alertdio,null);
                new AlertDialog.Builder(getActivity()).setTitle("请选择方向").setView(view).setIcon(R.drawable.way_icon).show();
                ib_up = (ImageButton) view.findViewById(R.id.ib_up);
                ib_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReceiverListener("U");
                    }
                });
                ib_left = (ImageButton) view.findViewById(R.id.ib_left);
                ib_left.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReceiverListener("L");
                    }
                });
                ib_right = (ImageButton) view.findViewById(R.id.ib_right);
                ib_right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReceiverListener("R");
                    }
                });
                ib_down = (ImageButton) view.findViewById(R.id.ib_down);
                ib_down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ReceiverListener("D");
                    }
                });
                break;
            case R.id.tv_set:
                final String set = "set set";
                ReceiverListener(set);
                LayoutInflater inflater1 = LayoutInflater.from(getActivity());
                view = inflater1.inflate(R.layout.set_set_alertdialog,null);
                new AlertDialog.Builder(getActivity()).setTitle("常规设置").setView(view).setIcon(R.drawable.ssset).show();
                sb_light = (SeekBar) view.findViewById(R.id.sb_light);
                sb_win = (SeekBar) view.findViewById(R.id.sb_win);
                sb_way_down = (SeekBar) view.findViewById(R.id.sb_down);
                sb_way_in = (SeekBar) view.findViewById(R.id.sb_in);
                sb_way_out = (SeekBar) view.findViewById(R.id.sb_out);
                sb_way_up = (SeekBar) view.findViewById(R.id.sb_up);
                tv_light_progress = (TextView) view.findViewById(R.id.alert_light);
                tv_win_progress = (TextView) view.findViewById(R.id.alert_win);
                tv_up_progress = (TextView) view.findViewById(R.id.alert_way_up);
                tv_down_progress = (TextView) view.findViewById(R.id.alert_way_down);
                tv_in_progress = (TextView) view.findViewById(R.id.alert_way_in);
                tv_out_progress = (TextView) view.findViewById(R.id.alert_way_out);
                SharedPreferences preferences = getActivity().getSharedPreferences("set",Context.MODE_PRIVATE);
                int light = preferences.getInt("light",0);
                int int_win = preferences.getInt("win",0);
                int int_up = preferences.getInt("up",0);
                int int_down = preferences.getInt("down",0);
                int int_out = preferences.getInt("out",0);
                int int_in = preferences.getInt("in",0);
                final SharedPreferences.Editor editor = getActivity().getSharedPreferences("set", Context.MODE_PRIVATE).edit();
                if (light!=0){
                    tv_light_progress.setText(light+"%");
                    sb_light.setProgress(light);
                }
                if (int_win!=0){
                    tv_win_progress.setText(int_win+"%");
                    sb_win.setProgress(int_win);
                }
                if (int_up!=0){
                    tv_up_progress.setText(int_up+"%");
                    sb_way_up.setProgress(int_up);
                }
                if (int_down!=0){
                    tv_down_progress.setText(int_down+"%");
                    sb_way_down.setProgress(int_down);
                }
                if (int_out!=0){
                    tv_out_progress.setText(int_out+"%");
                    sb_way_out.setProgress(int_out);
                }
                if (int_in!=0){
                    tv_in_progress.setText(int_in+"%");
                    sb_way_in.setProgress(int_in);
                }
                sb_light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_light_progress.setText(progress+"%");
                        editor.putInt("light",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                sb_win.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_win_progress.setText(progress+"%");
                        editor.putInt("win",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb_way_up.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_up_progress.setText(progress+"%");
                        editor.putInt("up",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb_way_down.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_down_progress.setText(progress+"%");
                        editor.putInt("down",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb_way_out.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_out_progress.setText(progress+"%");
                        editor.putInt("out",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                sb_way_in.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        tv_in_progress.setText(progress+"%");
                        editor.putInt("in",progress);
                        editor.commit();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
                break;
            default:
                break;
        }
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {//计时完毕时触发
            ReceiverListener("T");
            set_time_last.setText("0秒");
            et_hour.setInputType(InputType.TYPE_CLASS_TEXT);
            et_minute.setInputType(InputType.TYPE_CLASS_TEXT);


        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示
            et_hour.setInputType(InputType.TYPE_NULL);
            et_minute.setInputType(InputType.TYPE_NULL);
            set_time_last.setText(millisUntilFinished / 1000 + "秒");
        }
    }
}
