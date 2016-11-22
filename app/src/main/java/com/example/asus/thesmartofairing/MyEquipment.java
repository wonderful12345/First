package com.example.asus.thesmartofairing;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private View mView;
    private static final int CONNECTED = 1;
    private static final int RECEIVE = 0;

    private static final String HOST = "192.168.0.100";
    private static final int PORT = 5188;

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
                    Toast.makeText(getActivity(),"receive",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private void ReceiverListener(final String string){
            new Thread() {
                @Override
                public void run() {

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
        switch (v.getId()){
            case R.id.tv_time:
                String time = "give me time";
                ReceiverListener(time);
                new AlertDialog.Builder(getActivity()).setTitle("请输入时间").setIcon(R.drawable.give_me_time).setView(R.layout.set_time_alertdio).setPositiveButton("确定",null).setNegativeButton("取消",null).show();
                break;
            case R.id.tv_light:
                String light = "give me light";
                ReceiverListener(light);
                break;
            case R.id.tv_win:
                String win = "give me win";
                ReceiverListener(win);
                break;
            case R.id.tv_look:
                String look = "give me look";
                ReceiverListener(look);
                break;
            case R.id.tv_way:
                String way = "give me way";
                ReceiverListener(way);
                break;
            case R.id.tv_set:
                String set = "set set";
                ReceiverListener(set);
                break;
            default:
                break;
        }
    }
}
