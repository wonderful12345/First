package com.example.asus.thesmartofairing;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost mFragmentTabHost;
    private Class[] fragment = new Class[]{MyEquipment.class,StoreFragment.class,MineFragment.class};
    private int[] image = new int[]{R.drawable.ic_mine_select,R.drawable.ic_store_select,R.drawable.ic_my_select};
    private String[] textview = new String[]{"设备","商城","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        mFragmentTabHost = (FragmentTabHost) findViewById(R.id.fragmentTabHost_id);
        mFragmentTabHost.setup(MainActivity.this,getSupportFragmentManager(),R.id.realtabcontent);
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("商城")){
                }
            }
        });
        for (int i = 0; i < 3; i++) {
            View view = getLayoutInflater().inflate(R.layout.tabhost_layout,null);
            ImageView icon_image = (ImageView) view.findViewById(R.id.image_icon);
            TextView tv_icon = (TextView) view.findViewById(R.id.tv_icon);
            icon_image.setImageResource(image[i]);
            tv_icon.setText(textview[i]);
            mFragmentTabHost.addTab(mFragmentTabHost.newTabSpec(textview[i]).setIndicator(view),fragment[i],null);
        }
    }

}
